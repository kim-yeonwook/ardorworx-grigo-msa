package ibs.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.util.EUIGen;
import mecury.io.Bytes;
import mecury.io.LocalProperties;

public class CS_DELIVERY {

	@JsonView(JACKSONVIEW.AUTH.class)
	public String email = LocalProperties.get("lora.network.server.id", "admin");
	
	@JsonView(JACKSONVIEW.AUTH.class)
	public String password = LocalProperties.get("lora.network.server.pass", "admin");
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String serialNo;
	
	// 그때그떄 다름
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String applicationID = LocalProperties.get("lora.network.server.app", "6");
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String description;
	
	@JsonView({JACKSONVIEW.DEVICE.class, JACKSONVIEW.APPKEY.class})
	public String devEUI;
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String deviceProfileID = LocalProperties.get("lora.network.server.profile", "28d0d1ad-7b6a-47c5-b117-14dbbf15443d");
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String name;
	
	@JsonView(JACKSONVIEW.APPKEY.class)
	public String nwkKey;
	
	public static class JACKSONVIEW {
		
		public interface AUTH { }
		
		public interface DEVICE { }
		
		public interface APPKEY { }
	}
	
	public void decode() throws Exception {
		// TODO Auto-generated method stub
		EUIGen gen = new EUIGen().gen2Serial(this.serialNo);
		this.devEUI = gen.eui;
		this.nwkKey = Bytes.byte2hexstring(gen.toAppKey());
	}
	
	private static final class test {
		public static void main(String[] args) throws Exception {
			CS_DELIVERY d = new CS_DELIVERY();
			System.out.println(new ObjectMapper().writerWithView(CS_DELIVERY.JACKSONVIEW.AUTH.class).writeValueAsString(d));
			System.out.println(new ObjectMapper().writerWithView(CS_DELIVERY.JACKSONVIEW.DEVICE.class).writeValueAsString(d));
			System.out.println(new ObjectMapper().writerWithView(CS_DELIVERY.JACKSONVIEW.APPKEY.class).writeValueAsString(d));
		}
	}
}
