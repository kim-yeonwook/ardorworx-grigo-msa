package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.KIM;
import ibs.test.edge.Edge;
import mecury.io.Bytes;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;


@_ADVAction
public class SignalUpAction implements ADVAction {

	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {				
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				HashMap<String, Object> map = obj.readValue(body, HashMap.class);
				String MSG = (String)map.get("MSG");
				int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
				MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
				byte[] plan = Bytes.aes256dec(Edge.edge_list.get((int)map.get("USR_SEQ")).key(seed), MSG);
				
				System.out.println();
				System.out.println("---------------------------------------------------------------------------------");
				
				System.out.println(topic);
				System.out.println(obj.readValue(plan, HashMap.class));
				
				System.out.println("---------------------------------------------------------------------------------");
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		};
	}

	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/edge/signal/up";
	}

}
