package ibs.test.plc.lora;

import java.util.HashMap;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;

@_PLC(comm_code="ADC")
public class ADC extends MBPLC {
	
	@Override
	public void setParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] setReq() {
		// TODO Auto-generated method stub
		return null;
	}
}
