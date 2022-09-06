package ibs.test.plc.lora;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.DO;

@_PLC(comm_code="LORA.CS.DO")
public class DOMEM extends MBPLC{
	
	public String serial_no;
	public String comm_code;
	
	private DO signal;

	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal = (DO)signal;
	}
	
	@Override
	public byte[] setReq(byte[] _req) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int dataLength() {
		// TODO Auto-generated method stub
		return 0;
	}
}
