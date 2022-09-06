package ibs.test.plc.lora;

import java.util.HashMap;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.I2C;

@_PLC(comm_code="LORA.CS.I2C")
public class I2CMEM extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private I2C signal;
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal = (I2C)signal;
	}
	
	@Override
	public byte[] setReq() {
		// TODO Auto-generated method stub
		return null;
	}
}
