package ibs.test.signal.data;

import java.util.HashMap;

import ibs.test.signal.Signal;
import ibs.test.signal._SIG;

@_SIG(comm_code="DATA.AIR.SENSE")
public class AIR extends Signal {
	
	public String serial_no;
	public String comm_code;
	
	public HashMap<String, Object> vals;
	
	
}
