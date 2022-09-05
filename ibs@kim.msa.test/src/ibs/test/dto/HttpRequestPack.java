package ibs.test.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import ibs.test.util.EUIGen;
import mecury.io.Bytes;

public class HttpRequestPack extends IbsPack {
	
	@JsonInclude(Include.NON_NULL)
	public String serialNo;
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String applicationID;
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String description;
	
	@JsonView({JACKSONVIEW.DEVICE.class, JACKSONVIEW.APPKEY.class})
	public String devEUI;
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String deviceProfileID;
	
	@JsonView(JACKSONVIEW.DEVICE.class)
	public String name;
	
	@JsonView(JACKSONVIEW.APPKEY.class)
	public String nwkKey;
	
	public static class JACKSONVIEW {
		
		public interface DEVICE { }
		
		public interface APPKEY { }
	}
	
	@Override
	public void decode(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
//		super.decode(param);
		
		EUIGen gen = new EUIGen().gen2Serial((String)param.get("serialNo"));
		this.devEUI = gen.eui;
		this.nwkKey = Bytes.byte2hexstring(gen.toAppKey());
	}
}
