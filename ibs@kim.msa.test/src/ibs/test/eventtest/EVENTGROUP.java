package ibs.test.eventtest;

import java.util.ArrayList;

public class EVENTGROUP {

	public ArrayList<EVENT> events;
	
	public SIGNAL signal;
	
	public void add(EVENT t) throws Exception {
		if (events==null) events = new ArrayList<EVENT>();
		events.add(t);
	}
	
//	public void eval() throws Exception {
//		for (EVENT e : this.events) {
//			e.eval(signal);
//		}
//	}
	
	public void action() throws Exception {
		if (events==null) return;
		for (int index = 0; index < events.size(); index++) {
			events.get(index).action();
		}
	}
	
	public void setSignal(SIGNAL signal) throws Exception {
		this.signal = signal;
	}
	
}
