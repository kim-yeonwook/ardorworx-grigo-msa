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
import v3.venus.route.SvcRouter;

public class DownFrame {
	
	public String broker = LocalProperties.get("edge.broker", "tcp://192.168.0.170:1883");
	public String id = LocalProperties.get("edge.broker.id", "ardorworx");
	public String pass = LocalProperties.get("edge.broker.pass", "1q2w3e4r%^");
	
	public MqttClient client;
	
	public DownFrame() {
		try {
			client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
			
			MqttConnectOptions option = new MqttConnectOptions();
			option.setKeepAliveInterval(30);
			option.setCleanSession(true);
			option.setUserName(id);
			option.setPassword(pass.toCharArray());
			
			client.connect(option);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public void close() {
		try { client.disconnect(); } catch (Exception e) { e.printStackTrace(); }
		System.out.println(client.isConnected());
	}
	
}
