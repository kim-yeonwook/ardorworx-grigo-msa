package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.log.Log;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class EventUpActionSOP implements ADVAction {
	@Override
	public Callback action() {
		return (topic, body) -> {
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

				System.out.println();
				System.out.println("---------------------------------------------------------------------------------");
				
				System.out.println(topic);
				System.out.println();
				System.out.println(obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj.readValue(body, HashMap.class)));
				
				System.out.println("---------------------------------------------------------------------------------");
				
			} catch (Exception e) {
				Log.err(Modular.ID, "EVENT UP ACTION ERR", e);
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/event/noti";
	}
}
