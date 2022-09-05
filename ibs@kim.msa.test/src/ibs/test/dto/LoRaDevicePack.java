package ibs.test.dto;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoRaDevicePack extends IbsPack {
	
	public String serialNo;
	
	public String applicationID;
	
	public String description;
	
	public String devEUI;
	
	public String deviceProfileID;
	
	public String name;
	
	public byte[] getDeviceInfo() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("serialNo", serialNo);
		map.put("applicationID", applicationID);
		map.put("description", description);
		map.put("devEUI", devEUI);
		map.put("deviceProfileID", deviceProfileID);
		map.put("name", name);
		
		return new ObjectMapper().writeValueAsBytes(map);
	}
}
