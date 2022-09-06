package ibs.test.plc.lora;

import java.util.HashMap;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;

@_PLC(comm_code="LORA.CS.ADC")
public class ADC extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	public float val1;
	
	@Override
	public void setSignal(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] setReq() {
		// TODO Auto-generated method stub
		return null;
	}
}
