package ibs.test.down;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.io.LocalProperties;
import mecury.log.Log;
import v3.venus.mod.Modular;

@_Frame
public class DownFrame implements Frame {
	
	public String broker = LocalProperties.get("edge.broker", "tcp://192.168.0.170:1883");
	public String id = LocalProperties.get("edge.broker.id", "ardorworx");
	public String pass = LocalProperties.get("edge.broker.pass", "1q2w3e4r%^");
	
	public MqttClient client;
	
	
	@Override
	public void hello() throws Exception {
		// TODO Auto-generated method stub
		
		client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
		
		client.setCallback(new MqttCallback() {
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				// TODO Auto-generated method stub
				
				Log.debug(Modular.ID, topic);
				Log.debug(Modular.ID, message);
				
				try {
					
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}

			@Override
			public void connectionLost(Throwable arg0) {
				Log.err(Modular.ID, "Mosquitto connection lost", arg0);
				while (true) {
					try {
						client.reconnect();
					} catch (Exception e) {
						try { Thread.sleep(1000); } catch (Exception e2) {}
					}
					if(client.isConnected()) break;
				}
				System.out.println("CLIENT RECONNECTED");
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken arg0) {
				System.out.println("MESSEGE SEND SUCCESS");
			}

		});

		MqttConnectOptions option = new MqttConnectOptions();
		option.setKeepAliveInterval(30);
		option.setCleanSession(true);
		option.setUserName(id);
		option.setPassword(pass.toCharArray());
		client.connect(option);
		
	}
	
	@Override
	public void bye() throws Exception {
		// TODO Auto-generated method stub
		client.close();
	}
	
	@Override
	public void addListern(String[] arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void discardListern(String[] arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void pub(String topic, byte[] msg) throws Exception {
		// TODO Auto-generated method stub
		MqttMessage message = new MqttMessage(msg);
		client.publish(topic, message);

	}

	public void pub(String topic, HashMap<String,Object> msg) throws Exception {
		// TODO Auto-generated method stub
		MqttMessage message = new MqttMessage(new ObjectMapper().writeValueAsBytes(msg));
		client.publish(topic, message);

	}
}
