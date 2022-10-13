package ibs.test.device;

public class LDevice extends Device {
	
	@Override
	public boolean check() throws Exception {
		try {
			return (System.currentTimeMillis() - up_time) < term;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
