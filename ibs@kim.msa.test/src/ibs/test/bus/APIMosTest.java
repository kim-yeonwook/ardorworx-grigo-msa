package ibs.test.bus;

import java.util.HashMap;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.io.LocalProperties;
import mecury.log.Log;
import v3.api.venus.APIProxy;
import v3.api.venus.route.APIBus;
import v3.api.venus.route.APIStatus;
import v3.venus.mod.Modular;

public class APIMosTest implements APIBus {
	
	public static final String topic_header = LocalProperties.get("mos.topic.header");
	
	public String broker = LocalProperties.get("mos.broker");
	public String id = LocalProperties.get("mos.id");
	public String pass = LocalProperties.get("mos.pass");
	
	public boolean shutdown;
	
	public MqttClient client;
	protected HashMap<String,APIStatus> api_list = new HashMap<String,APIStatus>();
	
	
	@Override
	public HashMap<String,APIStatus> getUris() {
		return this.api_list;
	}
	
	@Override
	public void hello() throws Exception {
		
		client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
		client.setCallback(new MosCallBackTest() {
			@Override
			public void messageArrived(String topic_o, MqttMessage msg) throws Exception {
				// TODO Auto-generated method stub
				
				String topic = topic_o.substring(topic_header.length());
				try {
					if("ADV/api/lists".equals(topic)) {
						setAPIs(new ObjectMapper().readValue(msg.getPayload(), HashMap.class));
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
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
					System.out.println("REQUEST RECONNECTION");
					try { addListen(new String[] { "ADV/api/#"}); pub("ADV/MSA/api", "GET".getBytes()); } catch (Exception e) {}
				}
			}
		});

		MqttConnectOptions option = new MqttConnectOptions();
		option.setKeepAliveInterval(30);
		option.setCleanSession(true);
		option.setUserName(id);
		option.setPassword(pass.toCharArray());
		client.connect(option);
		
		addListen(new String[] { "ADV/api/#"});

		//MSA들로 부터 등록된 서비스 리스트를 전달 요청한다 
		pub("ADV/MSA/api", "GET".getBytes());
		
	}
	
	@Override
	public void addListen(String[] topic) throws Exception {
		for(int index=0;index<topic.length;index++) {
			topic[index] = topic_header + topic[index];
		}
		client.subscribe(topic);
	}
	@Override
	public void discardListen(String[] topic) throws Exception {
		for(int index=0;index<topic.length;index++) {
			topic[index] = topic_header + topic[index];
		}
		client.unsubscribe(topic);
	}
	
	
	@Override
	public void pub(String topic, byte[] msg) throws Exception {
		
		MqttMessage message = new MqttMessage(msg);
		client.publish(topic_header + topic, message);
	}
	
	@Override
	public void setAPIs(HashMap<String,List<String>> lists) {

		for(String api:lists.get("list")) {
			api_list.put(api, new APIStatus().reg());
		}
		if(null!=lists.get("deprecated")) {
			for(String api:lists.get("deprecated")) {
				api_list.remove(api);
			}
		}
		
		Log.info(APIProxy.ID, "reg aip count : " + api_list.size());
		for(String id:api_list.keySet()) {
			Log.info(APIProxy.ID, id + ": " + api_list.get(id).toReg());
		}
		
	}

	

	@Override
	public void bye() {
		try {
			shutdown = true;
			client.disconnect();
			client.close(true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
