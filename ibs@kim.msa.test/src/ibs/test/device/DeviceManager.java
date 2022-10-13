package ibs.test.device;

import java.util.HashMap;

import ibs.test.signal.loras.DI;

public class DeviceManager {

	private HashMap<String, Device> device_list = new HashMap<String, Device>();

	public Device get(String serial_no) {
		return device_list.get(serial_no);
	}
	
	public void put(Device device) {
		device_list.put(device.serial_no, device);
	}
	
	public void upSignal(String serial_no) {
		if (device_list.containsKey(serial_no)) device_list.get(serial_no).up_time = System.currentTimeMillis();
	}
	
	public void check() {
		
	}
	
	private static final class test {
		public static void main(String[] args) {

//			서버 기동시
			DeviceManager dm = new DeviceManager();
			
			Device device1 = new HDevice();
			device1.serial_no = "";
			device1.term = 0;
			device1.battery = 0;
			device1.status = false;
			
			dm.put(device1);
			
			Device device2 = new LDevice();
			device2.serial_no = "";
			device2.term = 0;
			device2.battery = 0;
			device2.status = false;
			
			dm.put(device2);
			
//			시그널 들어왔을떄 
			
			DI signal = new DI();
			signal.serial_no = "";
			signal.val1 = 1;
			signal.val2 = 0;
			
			dm.upSignal(signal.serial_no);
			
			dm.check();
			
		}
	}
	
}
