package ibs.test.dto;

import java.util.HashMap;

import ibs.test.edge.Edge;
import mecury.io.Bytes;

public class EdgePack extends _EdgePack {
	
	@Override
	public void decode(HashMap<String,Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		this.CNT_CD = (String)param.get("CNT_CD");
		this.USR_SEQ = (int)param.get("USR_SEQ");
		this.USR_TYPE = (int)param.get("USR_TYPE");
		this.ROLE = (int)param.get("ROLE");
		MSG = ((String)param.get("MSG")).replace('-', '+').replace('_', '/');
		plan = Bytes.aes256dec(Edge.edge_list.get(USR_SEQ).default_key, MSG);
		
	}
}
