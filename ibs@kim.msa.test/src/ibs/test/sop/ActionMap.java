package ibs.test.sop;

import java.util.HashMap;

public class ActionMap {
	
	private static final HashMap<String, Class<? extends ActionMap>> action_map= new HashMap<String, Class<? extends ActionMap>>();
	
	public static Class<? extends ActionMap> get(String id) throws Exception {
		return action_map.get(id);
	}
	
	public static void put(String id, Class<? extends ActionMap> view) throws Exception {
		action_map.put(id, view);
	}
}
