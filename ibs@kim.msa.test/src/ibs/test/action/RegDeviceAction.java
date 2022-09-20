package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.dto.CS_DELIVERY;
import ibs.test.edge.Edge;
import ibs.test.task.CS_Task;
import mecury.io.Bytes;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class RegDeviceAction implements ADVAction {

	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				HashMap<String, Object> param = obj.readValue(body, HashMap.class);
				String MSG = (String)param.get("MSG");
				int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
				MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
				byte[] plan = Bytes.aes256dec(Edge.edge_list.get(255).key(seed), MSG);
				
//				// param setting
//				CS_DELIVERY delivery = obj.readValue(plan, CS_DELIVERY.class);
//				delivery.decode();
//				
//				CS_Task cs_task = new CS_Task(delivery);
//				String jwt = cs_task.jwt();
//				cs_task.RegistCS(jwt);
				
				System.out.println();
				System.out.println("---------------------------------------------------------------------------------");
				
				System.out.println(topic);
				System.out.println();
				System.out.println(obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj.readValue(plan, HashMap.class)));
				
				System.out.println("---------------------------------------------------------------------------------");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/edge/255/node";
	}
	
}
