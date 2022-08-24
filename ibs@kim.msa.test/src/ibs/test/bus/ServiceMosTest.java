package ibs.test.bus;

import java.util.HashMap;
import java.util.concurrent.Executors;

import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.log.Log;
import mecury.util.TimeUtil;
import v3.venus.SSLSocketHandler;
import v3.venus.mod.Modular;
import v3.venus.route.ServiceMos;
import v3.venus.route.ServiceBus.Callback;

public class ServiceMosTest extends ServiceMos {
	
	public boolean shutdown;
	
	@Override
	public void hello() throws Exception {
	
		executor = Executors.newCachedThreadPool();
		
		client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
		client.setCallback(new MqttCallback() {

			@Override
			public void connectionLost(Throwable cause) {
				// TODO Auto-generated method stub
				Log.err(Modular.ID, "Mosquitto connection lost", cause);
				if (!shutdown) {
					while (true) {
						try {
							client.reconnect();
						} catch (Exception e) {
							try { Thread.sleep(1000); } catch (Exception e2) {}
						}
						if(client.isConnected()) break;
					}
					System.out.println("SERVICE RECONNECTION");
					try { if (map != null) addListern(map.list()); } catch (Exception e) {}
				}
			}

			@Override
			public void messageArrived(String topic_o, MqttMessage message) throws Exception {
				// TODO Auto-generated method stub
				
				Log.debug(Modular.ID, topic_o, message);
				String topic = topic_o.substring(topic_header.length());
				
				executor.submit(() -> {
					//response
					if(topic.startsWith("RES")) {
						listernRes(topic,message.getPayload());
					//service.call
					}else {
						listernSvc(topic, message.getPayload());
					}

				});

			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				// TODO Auto-generated method stub
				String[] tps = token.getTopics();
				for(String tp:tps) {
					Log.debug(Modular.ID, "-------------> " + tp);
				}
			}
			
		});
		

		MqttConnectOptions option = new MqttConnectOptions();
		option.setKeepAliveInterval(30);
		option.setCleanSession(true);
		option.setUserName(id);
		option.setPassword(pass.toCharArray());
		if(isSec) option.setSocketFactory(new SSLSocketHandler().getSSLSocket());
		client.connect(option);
		
		addListern(this.map.list());
		createSyncMap();
	}
	
//	@Override
//	public void serviceCall(String topic, HashMap<String, Object> msg, Callback callback) throws Exception {
//		serviceCall(topic, msg, callback, (a,b)->{
//			System.out.println("rollback.!!!!!!!");
//		});
//	}
//	
//	public void serviceCall(String topic, HashMap<String, Object> msg, Callback callback, Callback timeout) throws Exception {
//		// TODO Auto-generated method stub
//		
//				String syncId = "msa/" + TimeUtil.timeStamp()+TimeUtil.getOTP();
//				final String pubId = "RES/" + syncId; 
//				syncMap.put(pubId, callback);
//				
//				msg.put("syncId", syncId);
//				MqttMessage message = new MqttMessage(new ObjectMapper().writeValueAsBytes(msg));
//				
//				this.executor.submit(() -> {
//					
//					//정상인 경우 PUB에서 처리되므로, 여기서는 작업없이 진행 
//					//timeout 부분만 처리
//					try {
//						synchronized(callback) {
//							client.publish(topic_header + topic, message);
//							callback.wait(TIME_OUT);
//							//아직까지 남아있으면 RES PUB이 진행되지 않은 상태
//							if(null!=this.syncMap.remove(pubId)) {
//								//@ing
//							}
//						}
//						synchronized(timeout) {
//							client.publish(topic_header + topic, message);
//							timeout.wait(0);
//							//아직까지 남아있으면 RES PUB이 진행되지 않은 상태
//							if(null!=this.syncMap.remove(pubId)) {
//								//@ing
//							}
//						}
//					}catch(InterruptedException e) {
//						try {
//							
//						HashMap<String, Object> map = new HashMap<String, Object>();
//						map.put("isErr", "500");
//						listernRes(pubId, new ObjectMapper().writeValueAsBytes(map));
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}
//					catch(Exception e) {
//						e.printStackTrace();
//					}
//					
//				});
//	}
	
	@Override
	public void listernRes(String topic, byte[] payload) {
		// TODO Auto-generated method stub
		
		Callback callback = syncMap.remove(topic);
		if(null!=callback) {
			synchronized(callback) {
				callback.notifyAll();
			}
			executor.submit(() -> {
				callback.callback(topic, payload);
			});
		}
		
	}
	
	@Override
	public void serviceCall(String topic, HashMap<String, Object> msg, Callback callback) throws Exception {
		// TODO Auto-generated method stub
		
		String syncId = "msa/" + TimeUtil.timeStamp()+TimeUtil.getOTP();
		final String pubId = "RES/" + syncId; 
		syncMap.put(pubId, callback);
		
		msg.put("syncId", syncId);
		MqttMessage message = new MqttMessage(new ObjectMapper().writeValueAsBytes(msg));
		
		this.executor.submit(() -> {
			
			//정상인 경우 PUB에서 처리되므로, 여기서는 작업없이 진행 
			//timeout 부분만 처리
			try {
				synchronized(callback) {
					client.publish(topic_header + topic, message);
					callback.wait(TIME_OUT);
					//아직까지 남아있으면 RES PUB이 진행되지 않은 상태
					if(null!=this.syncMap.remove(pubId)) {
						//@ing
						synchronized(callback) {
							callback.notifyAll();
						}
						executor.submit(() -> {
							try {
								callback.callback(pubId, new ObjectMapper().writeValueAsBytes(new HashMap<String, Object>()));
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	@Override
	public void bye() {
		// TODO Auto-generated method stub
		this.shutdown = true;
		super.bye();
	}
	
}
