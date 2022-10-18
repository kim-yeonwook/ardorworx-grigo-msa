package ibs.test.action;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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

int app_id = LocalProperties.getInt("lora.network.server.app.id");
	
	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			MqttClient client = null;
			String broker = LocalProperties.get("probe.mq.broker");
			String id = LocalProperties.get("probe.mq.id");
			String pass = LocalProperties.get("probe.mq.pass");
			
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				HashMap<String, Object> param = obj.readValue(body, HashMap.class);
				String MSG = (String)param.get("MSG");
				int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
				MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
				byte[] plan = Bytes.aes256dec(Edge.edge_list.get((int)param.get("USR_SEQ")).key(seed), MSG);
				DOWN down = obj.readValue(plan, DOWN.class);
				
				client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
				
				MqttConnectOptions option = new MqttConnectOptions();
				option.setKeepAliveInterval(30);
				option.setCleanSession(true);
				option.setUserName(id);
				option.setPassword(pass.toCharArray());
				
				client.connect(option);
					
				client.publish("application/"+app_id+"/device/" + down.devEUI + "/command/down", new MqttMessage(obj.writeValueAsBytes(down)));
			
				client.disconnect(); client.close();
				Log.info(Modular.ID, "DownLink OK");
			} catch (Exception e) {
				if (client != null) try { client.disconnect(); client.close(); } catch (Exception e1) {}
				Log.err(Modular.ID, "DownLink Fail", e);
			} 
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/edge/255/lora/set";
	}
}
