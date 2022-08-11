package ibs.test.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DonwLinkPack extends IbsPack {

	public String uuid;
	
	public int edge_code = 255;
	
	@Override
	public byte[] encode() throws Exception {
		return new ObjectMapper().writeValueAsBytes(this);
	}
	
}
