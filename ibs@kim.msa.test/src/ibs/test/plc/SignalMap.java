package ibs.test.plc;

import java.util.HashMap;

public class SignalMap {
	
	private static final HashMap<String, Class<? extends MBPLC>> signal_map= new HashMap<String, Class<? extends MBPLC>>();
	
	public static Class<? extends MBPLC> get(String id) throws Exception {
		return signal_map.get(id);
	}
	
	public static void put(String id, Class<? extends MBPLC> view) throws Exception {
		signal_map.put(id, view);
	}
}
