package ibs.test.plc.lora;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.DI;

@_PLC(comm_code="LORA.CS.DI")
public class DIMEM extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private DI signal;
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal = (DI)signal;
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
