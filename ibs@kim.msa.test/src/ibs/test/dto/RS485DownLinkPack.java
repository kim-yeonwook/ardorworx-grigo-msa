package ibs.test.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.util.EUIGen;

public class RS485DownLinkPack extends IbsPack {
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String serial_no;
	
	@JsonProperty(access = Access.READ_ONLY)
	private boolean confirmed = true;
	
	@JsonProperty(access = Access.READ_ONLY)
	private int fPort = 106;
	
	@JsonProperty(access = Access.READ_WRITE)
	private String data;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public EUIGen gen;
	
	@Override
	public void decode(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		super.decode(param);
		gen = new EUIGen().gen2Serial(serial_no);
		
	}
	
	@Override
	public byte[] encode() throws Exception {
		// TODO Auto-generated method stub
		return new ObjectMapper().writeValueAsBytes(this);
	}
	
	public String getTopic() throws Exception {
		return "application/6/device/" + gen.eui + "/command/down";
	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				HashMap<String, Object> map = new HashMap<>();
				map.put("serial_no", "ada");
				
				RS485DownLinkPack pack = new ObjectMapper().readValue(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsBytes(map), RS485DownLinkPack.class);
				System.out.println(pack.serial_no);
				System.out.println(new ObjectMapper().writeValueAsString(pack));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
