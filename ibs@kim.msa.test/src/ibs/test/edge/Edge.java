package ibs.test.edge;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.MariaPool;
import ibs.test.Xery;
import ibs.test.dto.edge.EdgePack;
import mecury.io.Bytes;
import xery.mecury.GridSet;
import xery.mecury.Result;
import xery.mecury.XeryDAO;

public class Edge {
	
	public static Hashtable<Integer,Edge> edge_list = new Hashtable<Integer,Edge>(); 
	
	private final static int iv_size = 32; 
	private final static int iv_keys_size = 4;
	private final byte[][] ivs = new byte[iv_keys_size][iv_size];
	
	public HashMap<String, ArrayList<HashMap<String, Object>>> begin_data = new HashMap<String, ArrayList<HashMap<String, Object>>>();
	
	public int id;
	public String edge_name;
	public int edge_stat;
	public byte[] default_key;
	public boolean isCon;

	public byte[] key(int index) {
		
		return ivs[index%4];
	}
	
	public void resetKey(EdgePack pack) throws Exception {
		
		byte[] key = new byte[iv_size];
		ivs[0] = key;
		int offset = 0;
		System.arraycopy(pack.plan, offset, key, 0, iv_size);
		 
		key = new byte[iv_size];
		ivs[1] = key;
		offset += iv_size;
		System.arraycopy(pack.plan, offset, key, 0, iv_size);

		key = new byte[iv_size];
		ivs[2] = key;
		offset += iv_size;
		System.arraycopy(pack.plan, offset, key, 0, iv_size);

		key = new byte[iv_size];
		ivs[3] = key;
		offset += iv_size;
		System.arraycopy(pack.plan, offset, key, 0, iv_size);

		isCon = true;
		
		System.out.println("\n" + new String(Base64.getEncoder().encode(ivs[0])));
		System.out.println("\n" + new String(Base64.getEncoder().encode(ivs[1])));
		System.out.println("\n" + new String(Base64.getEncoder().encode(ivs[2])));
		System.out.println("\n" + new String(Base64.getEncoder().encode(ivs[3])));
		
	}
	
	public HashMap<String, Object> decode(HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		String e = Bytes.aes256enc(this.default_key, new ObjectMapper().writeValueAsBytes(map))
				.replace('+', '-').replace('/', '_').replace("=", "");
		param.put("msg", e);
		
		return param;
	}
	
	public static void setEdge() throws Exception {
		try(Connection con = MariaPool.getConnectio()) {
			StringBuffer buffer = new StringBuffer();
			XeryDAO dao = new XeryDAO(Xery.xery, con, buffer);
			Result rs = dao.queryRun("edge.select_edge_list", new GridSet());
			
			for (int index = 0; index < rs.RESULT_CNT; index++) {
				Edge e = new Edge();
				e.id = rs.getInt("id", index);
				e.edge_name = rs.getStr("edge_name", index);
				e.edge_stat = rs.getInt("edge_stat", index);
				e.default_key = rs.getStr("aes_key", index).getBytes();
				Edge.edge_list.put(e.id, e);
			}
			System.out.println(Edge.edge_list);
			System.out.println(buffer.toString());
			
		} 
	}
}
