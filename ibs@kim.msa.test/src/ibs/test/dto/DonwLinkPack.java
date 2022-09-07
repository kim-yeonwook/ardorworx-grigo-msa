package ibs.test.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DonwLinkPack extends IbsPack {
	
	public String serial_no;
	
	public String data;
	
	@Override
	public byte[] encode() throws Exception {
		// TODO Auto-generated method stub
		return new ObjectMapper().writeValueAsBytes(this);
	}
}
