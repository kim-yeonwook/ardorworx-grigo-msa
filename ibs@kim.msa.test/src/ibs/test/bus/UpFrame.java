package ibs.test.bus;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import mecury.io.LocalProperties;

public class UpFrame {

	public String broker = LocalProperties.get("probe.mq.broker");
	public String id = LocalProperties.get("probe.mq.id");
	public String pass = LocalProperties.get("probe.mq.pass");
	
	public MqttClient client;
	
	public boolean isClose;
	
	public static final String CNT_CD = LocalProperties.get("probe.cnt");
	
	public void open() throws Exception {
		
		client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
		client.setCallback(new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage msg) throws Exception {
				// TODO Auto-generated method stub
				
				// 
				
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectionLost(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MqttConnectOptions option = new MqttConnectOptions();
		option.setKeepAliveInterval(30);
		option.setCleanSession(true);
		option.setUserName(id);
		option.setPassword(pass.toCharArray());
		client.connect(option);
		
		client.subscribe("");
		
	}
	
	public void close() throws Exception {
		if (client!=null) { 
			try { client.disconnect();} catch (Exception e) {}
			try { client.close(); } catch (Exception e) {}
		}
	}
}
