package ibs.test.eventtest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import v3.venus.route.ADVRouter;

public class EVENT {
	
//	public boolean status;
//	
//	public ArrayList<THRESHOLD> thresholds;
	
	public void action() throws Exception {
		ADVRouter.pub("ADV/a", new ObjectMapper().writeValueAsBytes(this));
	}
	
//	public void eval(SIGNAL signal) throws Exception {
//		for (THRESHOLD t : this.thresholds) {
//			if (t.eval(signal));
//		}
//	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				// 1. 시그널 발생
				SIGNAL signal = new SIGNAL();

				// 2. 올라온 시그널로 발생할 수 있는 이벤트들의 임계치 검색 (시그널 하나당 이벤트 여러개 가능)
				
				List<THRESHOLD> t_group = new ArrayList<THRESHOLD>();

				// 3. 시그널을 임계치로 평가
				for (THRESHOLD t : t_group) {
					if (t.eval(signal)) {
						
						// 4. 이벤트 발생
						EVENT e = new EVENT();
						
						
						// 5. 이벤트 ADV
						e.action();
					}
				}
				
				/////////////////////////////////////////////////////////////////////////////////////
//				// 1. 시그널 발생
//				SIGNAL signal = new SIGNAL();
//				
//				// 2. 올라온 시그널로 발생할 수 있는 이벤트들의 임계치 검색 (시그널 하나당 이벤트 여러개 가능)
//				EVENTGROUP e_group = new EVENTGROUP();
//				e_group.setSignal(signal);
//				
//				
//				// 3. 시그널 임계치 평가
//				e_group.eval();
//				
//				
//				// 4. 이벤트 액션
//				e_group.action();
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
