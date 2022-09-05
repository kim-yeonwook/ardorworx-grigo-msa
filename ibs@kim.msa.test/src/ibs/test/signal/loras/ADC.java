package ibs.test.signal.loras;

import ibs.test.signal.Signal;
import ibs.test.signal._SIG;

@_SIG(comm_code="LORA.CS.ADC")
public class ADC extends Signal {
	
	public String serial_no;
	public String comm_code;
		
	public float val1;
		
}
