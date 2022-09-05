package ibs.test.signal;

import java.util.HashMap;

public class SignalMap {

	private static final HashMap<String, Class<? extends SignalIF>> map = new HashMap<String, Class<? extends SignalIF>>();
	
	
	
	public static Class<? extends SignalIF> get(String comm_code)  {
		return map.get(comm_code);
	}
	
	public static void put(String code, Class<? extends SignalIF> cls) {
		map.put(code, cls);
	}
	
	public static String info() {
		return map.toString();
	}
	
}
