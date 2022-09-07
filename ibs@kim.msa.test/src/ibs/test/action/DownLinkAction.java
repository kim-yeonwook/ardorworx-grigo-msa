package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ibs.test.down.DownFrame;
import ibs.test.dto.RS485DownLinkPack;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class DownLinkAction implements ADVAction {

	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				obj.enable(SerializationFeature.WRAP_ROOT_VALUE);
				
				RS485DownLinkPack pack = obj.readValue(body, RS485DownLinkPack.class);
				HashMap<String, Object> map = obj.readValue(body, HashMap.class);
				pack.decode(map);
				
				DownFrame frame = new DownFrame();
				frame.pub("" + pack.getTopic() , pack.encode());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/down/signal";
	}
}
