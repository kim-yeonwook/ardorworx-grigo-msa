package ibs.test.plc.lora;

import java.util.ArrayList;
import java.util.List;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.DI;

@_PLC(comm_code="DI")
public class DIMEM extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private List<Integer> signal = new ArrayList<Integer>();
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal.add(((DI)signal).val1);
		this.signal.add(((DI)signal).val2);
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
