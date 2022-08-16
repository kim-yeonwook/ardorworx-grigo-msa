package ibs.test.bus;

import java.util.concurrent.Executors;

import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import mecury.log.Log;
import v3.venus.SSLSocketHandler;
import v3.venus.mod.Modular;
import v3.venus.route.ServiceMos;

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
	
	@Override
	public void bye() {
		// TODO Auto-generated method stub
		this.shutdown = true;
		super.bye();
	}
	
}
