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
		super.hello();
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
