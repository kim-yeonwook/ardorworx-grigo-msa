package ibs.test.down;

import java.util.Base64;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.down.signal.RS485;

public abstract class Things {
	
	public final int DEFAULT_FPORT = 100;
	
	public final int MAX_BYTE_SIZE = 26;
	
	public final int START_INDEX_SIZE = 2;
	
//	public final static int MAX_VALUE_BYTE_SIZE = 24;
 
	public int interface_type;
	
	public int sensor_number;
	
	private String data;
	
	public void encode() throws Exception {
		byte[] value = new byte[MAX_BYTE_SIZE];
		
		value[0] = (byte)interface_type;
		value[1] = (byte)sensor_number;
		
		 data = new String(Base64.getEncoder().encode(this.encode_value(value)));
	}
	
	public abstract byte[] encode_value(byte[] value) throws Exception;
	
	public byte[] down() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("confirmed", true);
		map.put("fPort", DEFAULT_FPORT + interface_type);
		map.put("data", data);
		
		System.out.println(map.toString());
		return new ObjectMapper().writeValueAsBytes(map);
	}
	
	private final static class test {
		public static void main(String[] args) throws Exception {
//			RS485 VENTAX
//			BQICAQAAAAEDFAAKBAAAAAQAAAAEAAAAAAA=
//			05 02 02 01 00 00 00 01 03 14 00 0A 04 00 00 00 04 00 00 00 04 00 00 00 00 00
			byte[] data = Base64.getDecoder().decode("BQICAQAAAAEDFAAKBAAAAAQAAAAEAAAAAAA=");
			
				
			RS485 t = new RS485();
//			t.uuid = "f22b7f0404fd0005";
//			t.fPort = 105;
			t.encode();
			
		}
	}
}
