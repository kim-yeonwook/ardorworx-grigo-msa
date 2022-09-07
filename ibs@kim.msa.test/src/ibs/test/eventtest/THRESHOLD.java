package ibs.test.eventtest;

import java.util.ArrayList;

public class THRESHOLD {
	
	public ArrayList<CALCULATION> calc;
	
	public boolean eval(SIGNAL signal) throws Exception {
		
		for(CALCULATION c : calc) {
			if(c.calc()) return true;
		}
		
		return false;
		
	}

}
