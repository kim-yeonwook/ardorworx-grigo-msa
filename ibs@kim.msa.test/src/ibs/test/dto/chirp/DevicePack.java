package ibs.test.dto.chirp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import ibs.test.util.EUIGen;
import mecury.io.Bytes;
import mecury.io.LocalProperties;

public class DevicePack {
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String serialNo;
	
	@JsonView(JACKSONVIEWS.DEVICE.class)
	public String applicationID = LocalProperties.get("lora.network.server.app.id");
	
	@JsonView(JACKSONVIEWS.DEVICE.class)
	public String description;
	
	@JsonView({JACKSONVIEWS.DEVICE.class, JACKSONVIEWS.APPKEY.class})
	public String devEUI;
	
	@JsonView(JACKSONVIEWS.DEVICE.class)
	public String deviceProfileID = LocalProperties.get("lora.network.server.dvcpro.id");
	
	@JsonView(JACKSONVIEWS.DEVICE.class)
	public String name;
	
	@JsonView(JACKSONVIEWS.APPKEY.class)
	public String nwkKey;
	
	
	
	public static class JACKSONVIEWS {
		
		public interface DEVICE { }
		
		public interface APPKEY { }
		
	}
	
	public void decode() throws Exception {
		EUIGen gen = new EUIGen().gen2Serial(serialNo);
		devEUI = gen.eui;
		nwkKey = Bytes.byte2hexstring(gen.toAppKey());
	}
}
