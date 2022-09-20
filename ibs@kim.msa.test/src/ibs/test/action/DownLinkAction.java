package ibs.test.action;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.dto.DOWN;
import ibs.test.edge.Edge;
import mecury.io.Bytes;
import mecury.io.LocalProperties;
import mecury.log.Log;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class DownLinkAction implements ADVAction {

	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			MqttClient client = null;
			String broker = LocalProperties.get("edge.broker", "tcp://192.168.0.170:1883");
			String id = LocalProperties.get("edge.broker.id", "ardorworx");
			String pass = LocalProperties.get("edge.broker.pass", "1q2w3e4r%^");
			
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				HashMap<String, Object> param = obj.readValue(body, HashMap.class);
				String MSG = (String)param.get("MSG");
				int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
				MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
				byte[] plan = Bytes.aes256dec(Edge.edge_list.get(255).key(seed), MSG);
				DOWN down = obj.readValue(plan, DOWN.class);
				
//				client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
//				
//				MqttConnectOptions option = new MqttConnectOptions();
//				option.setKeepAliveInterval(30);
//				option.setCleanSession(true);
//				option.setUserName(id);
//				option.setPassword(pass.toCharArray());
				
//				client.connect(option);
				
				System.out.println();
				System.out.println("---------------------------------------------------------------------------------");
				
				System.out.println(topic);
				System.out.println();
				System.out.println(obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj.readValue(plan, HashMap.class)));
				
				System.out.println("---------------------------------------------------------------------------------");
					
//				client.publish("application/6/device/" + down.devEUI + "/command/down", new MqttMessage(obj.writeValueAsBytes(down)));
			
//				client.disconnect();
				Log.info(Modular.ID, "DownLink OK");
			} catch (Exception e) {
				if (client != null) try { client.disconnect(); } catch (Exception e1) {}
				Log.info(Modular.ID, "DownLink Fail");
				Log.info(Modular.ID, e);
			} 
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/ADV/ADV/ADV";
	}
}
