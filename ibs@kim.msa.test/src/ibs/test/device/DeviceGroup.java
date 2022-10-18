package ibs.test.device;

import java.util.HashMap;

public class DeviceGroup {
	
	public static HashMap<String, IDevice> device_list = new HashMap<String, IDevice>();
	
	public static HashMap<String, IDevice> n_device_list = new HashMap<String, IDevice>();
	
	public static HashMap<String, IDevice> e_device_list = new HashMap<String, IDevice>();
	
	Monitor m;
	
	public static void put(String serial_no, IDevice device) {
		n_device_list.put(serial_no, device);
	}
	
	public static IDevice get(String serial_no) {
		return n_device_list.get(serial_no);
	}
	
	public static IDevice remove(String serial_no) {
		return n_device_list.remove(serial_no);
	}
	
	public void isErr(String serial_no) {
		e_device_list.put(serial_no, n_device_list.get(serial_no));
	}
	
	public void isNml(String serial_no) {
		n_device_list.put(serial_no, e_device_list.get(serial_no));
	}
	
}
