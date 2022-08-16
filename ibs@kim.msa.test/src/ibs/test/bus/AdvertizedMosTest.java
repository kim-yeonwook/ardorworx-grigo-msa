package ibs.test.bus;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import mecury.log.Log;
import v3.venus.SSLSocketHandler;
import v3.venus.mod.Modular;
import v3.venus.route.AdvertizedMos;

public class AdvertizedMosTest extends AdvertizedMos {
	
	public boolean shutdown;
	
	@Override
	public void hello() throws Exception {
		
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
					System.out.println("ADVERTIZED RECONNECTION");
					try { if (topics != null) addListern(listerner.keySet().toArray(new String[listerner.size()])); } catch (Exception e) {}
				}
			}

			@Override
			public void messageArrived(String topic_o, MqttMessage message) throws Exception {
				// TODO Auto-generated method stub
				
				String topic = topic_o.substring(topic_header.length());
				listerner.get(topic).callback(topic, message.getPayload());
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
		
		this.addListern(topics);
	}
	
	@Override
	public void bye() {
		this.shutdown = true;
		super.bye();
	}
	
//	@Override
//	public void addListern(String[] topic) throws Exception {
//		// TODO Auto-generated method stub
//		
//		//@ing 테스팅 해봐야 함.
//		//subscribe 하면 기존 토픽 제거됨 
//		for(int index=0;index<topic.length;index++) {
//			topic[index] = topic_header + topic[index];
//		}
//		this.client.subscribe(topic);
//		
//		Log.info(Modular.ID, "+++++++++ Advertized listern total cnt :" + topic.length);
//		for(String t:topic) {
//			Log.info(Modular.ID, "+++++++++ Advertized listern : " + t);
//		}
//
//	}
	
//	@Override
//	public 	void preset(TaskMap map, HashMap<String, AdvertizedBus.Callback> listerner)  throws Exception{
//		this.map = map;
//		if(null==listerner)	listerner = new HashMap<String, AdvertizedBus.Callback>();
//		this.listerner = listerner;
//		
//		listerner.put("ADV/MSA/api", (topic, payloader) -> {
//			
//			HashMap<String,String[]> lists = new HashMap<String,String[]>();
//			lists.put("list", map.list());
//			try {
//				byte[] msg = new ObjectMapper().writeValueAsBytes(lists);
//				pub("ADV/api/lists", msg);
//			}catch(Exception e) {
//				Log.err(Modular.ID, "api advertized err", e);
//			}
//		});
//
//		topics = listerner.keySet().toArray(new String[listerner.size()]);
//		
//		for (int index = 0; index < topics.length; index++) {
//			System.out.println("TOPICS"+index+" : " + topics[index]);
//		}
//		System.out.println("Map : " + map);
//		System.out.println("KEY_SET : " + listerner.keySet());
//		System.out.println("LISTEN : " + listerner.keySet().toArray(new String[listerner.size()]));
//	}

}
