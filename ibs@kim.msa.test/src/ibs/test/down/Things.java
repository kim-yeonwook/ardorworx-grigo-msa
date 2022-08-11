package ibs.test.down;

import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.down.signal.RS485;
import ibs.test.util.BytesTest;
import mecury.io.Bytes;

public abstract class Things {
 
	public String uuid;
	
	public int fPort;
	
	public String data;
	
	public void encode() throws Exception {
		this.encode_value();
		byte[] payload = new ObjectMapper().writeValueAsBytes(this);
	}
	
	public abstract String encode_value() throws Exception;
	
	private final static class test {
		public static void main(String[] args) throws Exception {
//			RS485 VENTAX
//			BQICAQAAAAEDFAAKBAAAAAQAAAAEAAAAAAA=
//			05 02 02 01 00 00 00 01 03 14 00 0A 04 00 00 00 04 00 00 00 04 00 00 00 00 00
			byte[] data = Base64.getDecoder().decode("BQICAQAAAAEDFAAKBAAAAAQAAAAEAAAAAAA=");
			
				
			RS485 t = new RS485();
			t.uuid = "f22b7f0404fd0005";
			t.fPort = 105;
			t.encode();
			
		}
	}
}
