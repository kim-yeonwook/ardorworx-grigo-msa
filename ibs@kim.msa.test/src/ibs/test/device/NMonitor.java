package ibs.test.device;

import com.fasterxml.jackson.databind.ObjectMapper;

import v3.venus.route.ADVRouter;

public class NMonitor extends Monitor {
	
	public void checkStatus() throws Exception {
		for (int index = 0; index < DeviceGroup.n_device_list.size(); index++) {
			IDevice d =DeviceGroup.n_device_list.get(index);
			d.check();
			
			ADVRouter.pub("ADV/node/", new ObjectMapper().writeValueAsBytes(d));
			
//			if ()
		}
	}
	
	
}
