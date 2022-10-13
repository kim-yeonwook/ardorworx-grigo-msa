package ibs.test.device;

import java.net.InetAddress;

public class HDevice extends Device {
	
	public boolean check() throws Exception {
		try {
			return status = InetAddress.getByName("192.168.0.11").isReachable(1000);
		} catch (Exception e) {
			return false;
		}
	}
}
