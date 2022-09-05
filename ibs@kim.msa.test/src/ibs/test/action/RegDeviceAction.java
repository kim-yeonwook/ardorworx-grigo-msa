package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ibs.test.csrest.HttpSender;
import ibs.test.csrest.cs.APPKEY;
import ibs.test.csrest.cs.INDEVICE;
import ibs.test.dto.HttpRequestPack;
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
				obj.enable(SerializationFeature.WRAP_ROOT_VALUE);
				
				HttpRequestPack pack = obj.readValue(body, HttpRequestPack.class);
				pack._payload(body);
				pack._logTrace(new StringBuffer());
				pack.decode(obj.readValue(body, HashMap.class));

				// insert device
				HttpSender sender1 = new HttpSender();
				sender1.set(new INDEVICE().getHttpUrlConnection("/api/devices"));
				sender1.send(obj.writerWithView(HttpRequestPack.JACKSONVIEW.DEVICE.class).withRootName("device").writeValueAsBytes(pack));
				
				System.out.println(sender1.receive());
				
				System.out.println();
				
				// setting app_key
				HttpSender sender2 = new HttpSender();
				sender2.set(new APPKEY().getHttpUrlConnection("/api/devices/" + pack.devEUI + "/keys"));
				sender2.send(obj.writerWithView(HttpRequestPack.JACKSONVIEW.APPKEY.class).withRootName("deviceKeys").writeValueAsBytes(pack));
				
				System.out.println(sender2.receive());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/LORA/DEVICE";
	}
	
}
