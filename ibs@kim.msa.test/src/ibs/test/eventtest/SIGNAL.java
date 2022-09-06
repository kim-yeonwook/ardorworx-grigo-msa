package ibs.test.eventtest;

import java.util.HashMap;

public class SIGNAL {

	public String serial_no;
	
	public String val1;
	
	public HashMap<String, Object> getSignal() throws Exception {
		HashMap<String, Object> signal = new HashMap<String, Object>();
		
		return signal;
	}
	
	public EVENT eval(THRESHOLD t) throws Exception {

		for (CALCULATION c : t.calc) {
			
		}
		
		return null;
	}
}
