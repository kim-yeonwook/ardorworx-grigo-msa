package ibs.test.device;

public abstract class IDevice {
	/** 상태
	 * 	1. last seen 확인
	 *  2. heart beat 확인
	 *  
	 */
	
	public String serial_no;
	
	public long last_signal;
	
	public int status;
	
	public int term;
	
	public abstract void check() throws Exception;
		
	
	private static final class test {
		public static void main(String[] args) {
			try {
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
