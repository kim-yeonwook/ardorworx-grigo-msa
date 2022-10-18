package ibs.test.signal.data;

import java.util.HashMap;

import ibs.test.signal.Signal;
import ibs.test.signal._SIG;

@_SIG(comm_code="DATA.WT.SENSE")
public class WT extends Signal {
	
	public String serial_no;
	public String comm_code;
	
	public HashMap<String, Object> vals;
	
	
}
