package ibs.test.device;

import ibs.test.device.com.LORA;
import xery.mecury.Result;

public class DevcieScheduler {
	
	public static final int MIN = 60*1000;
	public static final int FIVE_MIN = 5*60*1000;

	public void open() throws Exception {
		
		Result rs = new Result();
		for (int index=0; index < rs.RESULT_CNT; index++) {
			IDevice device = new LORA();
			DeviceGroup.put(rs.getStr("serial_no", index), device);
		}
		
		// 정상 디바이스 쓰레드
		// 5분간격 확인
		new Thread() {
			public void run() {
				while(true) {
					try {
						NMonitor m = new NMonitor();
						
						
						Thread.sleep(FIVE_MIN);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		// 비정상 디바이스 쓰레드
		// 1분 간격 확인
		new Thread() {
			public void run() {
				while(true) {
					try {
						EMonitor m = new EMonitor();
						
						Thread.sleep(MIN);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public static final class test {
		public static void main(String[] args) {
			try {
				// 서버 시작시 디바이스 세팅  DB
				DevcieScheduler schedule = new DevcieScheduler();
				schedule.open();
				
				
				
//				upsingal시 해당 디바이스의 last signal을 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
