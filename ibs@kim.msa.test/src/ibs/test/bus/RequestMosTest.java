package ibs.test.bus;

import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import mecury.log.Log;
import v3.venus.SSLSocketHandler;
import v3.venus.mod.Modular;
import v3.venus.route.RequestMos;

public class RequestMosTest extends RequestMos {

	@Override
	public void hello() throws Exception {
		
		executor = Executors.newCachedThreadPool();
		
		client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
		client.setCallback(new MqttCallback() {
			@Override
			public void messageArrived(String topic_o, MqttMessage message) throws Exception {
				// TODO Auto-generated method stub
				
				Log.debug(Modular.ID, topic_o, message);
				String topic = topic_o.substring(topic_header.length());
				
				executor.submit(() -> {
					listernSvc(topic, message.getPayload());
				});

			}

			@Override
			public void connectionLost(Throwable cause) {
				// TODO Auto-generated method stub
				Log.err(Modular.ID, "Mosquitto connection lost", cause);
				while (true) {
					try {
						client.reconnect();
					} catch (Exception e) {
						try { Thread.sleep(1000); } catch (Exception e2) {}
					}
					if(client.isConnected()) break;
				}
				System.out.println("REQUEST RECONNECTION");
				try { if (map != null) addListern(map.list()); } catch (Exception e) {}
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
	}
	
}
