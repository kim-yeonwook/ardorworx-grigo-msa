package ibs.test.eventtest;

import java.util.ArrayList;

public class EVENTTHRESHOLD {
	
	public ArrayList<THRESHOLD> thresHolds = new ArrayList<THRESHOLD>();
	
	public void add(THRESHOLD t) throws Exception {
		thresHolds.add(t);
	}
	
	public void eval() throws Exception {
		if (thresHolds!=null) {
			for (int index = 0; index < thresHolds.size(); index++) {
			}
			
		}
	}
	public EVENTGROUP eval(SIGNAL signal) throws Exception {
		EVENTGROUP group = new EVENTGROUP();
		for (THRESHOLD t : thresHolds) {
		}
		return group;
	}

}
