package ibs.test.plc.lora;

import java.util.HashMap;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;

@_PLC(comm_code="LORA.CS.UART")
public class UART extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private short val1;
	private short val2;
	private short val3;
	private short val4;
	private short val5;
	private short val6;
	private short val7;
	private short val8;
	
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
