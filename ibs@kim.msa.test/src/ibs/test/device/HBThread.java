package ibs.test.device;

public class HBThread{

	public boolean isEnd;
	
	public void open() {
		
		new Thread() {
			@Override
			public void run() {
				
				
//				10분에 한번 확인
				while(!isEnd) {
					try {
						
						
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void close() {
		this.isEnd = true;
	}
}
