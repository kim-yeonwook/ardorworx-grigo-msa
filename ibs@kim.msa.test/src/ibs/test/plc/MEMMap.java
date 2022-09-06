package ibs.test.plc;

import java.util.HashMap;

public class MEMMap {

	private static final HashMap<String, Integer> mem = new HashMap<String, Integer>();
	
	public static void put(String key, Integer value) {
		mem.put(key, value);
	}
	
	public static int get(String key) {
		return mem.get(key);
	}
}
