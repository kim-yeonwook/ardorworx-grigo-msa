package ibs.test.sop;

import java.util.HashMap;

public class ViewMap {
	
	private static final HashMap<String, Class<? extends SOPView>> view_map= new HashMap<String, Class<? extends SOPView>>();
	
	public static Class<? extends SOPView> get(String id) throws Exception {
		return view_map.get(id);
	}
	
	public static void put(String id, Class<? extends SOPView> view) throws Exception {
		view_map.put(id, view);
	}
	
}
