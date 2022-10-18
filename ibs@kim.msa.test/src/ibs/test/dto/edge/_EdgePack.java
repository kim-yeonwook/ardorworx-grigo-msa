package ibs.test.dto.edge;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.dto.IbsPack;
import ibs.test.edge.Edge;
import mecury.io.Bytes;

public class _EdgePack extends IbsPack {
	
	@JsonIgnore public String MSG;
	@JsonIgnore public byte[] plan;

	@JsonInclude(Include.NON_NULL) public String rtn;
	
	@Override
	public void decode(HashMap<String,Object> param) throws Exception {
		// TODO Auto-generated method stub
		super.decode(param);
		
		MSG = (String)param.get("MSG");
		int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
		MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
		plan = Bytes.aes256dec(Edge.edge_list.get(USR_SEQ).key(seed), MSG);
		
	}
	
	
	@Override
	public byte[] encode() throws Exception{
		// TODO Auto-generated method stub
		
		byte[] msg1 = new ObjectMapper().writeValueAsBytes(this);
		int seed = (int)(Math.random()*100);
		String e = Bytes.aes256enc(Edge.edge_list.get(USR_SEQ).key(seed), msg1)
							.replace('+', '-').replace('/', '_').replace("=", "");
		e = e + Bytes.byte2hex(seed);

		HashMap<String,Object> msg2 = new HashMap<String,Object>();
		msg2.put("isErr", false);
		msg2.put("msg", e);

		return new ObjectMapper().writeValueAsBytes(msg2);

	}
}
