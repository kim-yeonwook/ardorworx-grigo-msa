package ibs.test.device;

public abstract class Device {
	
	public String serial_no;
	
	public long up_time;
	
	public int term;
	
	public int battery;
	
	public boolean status;
	
	public abstract boolean check() throws Exception;
	
}
