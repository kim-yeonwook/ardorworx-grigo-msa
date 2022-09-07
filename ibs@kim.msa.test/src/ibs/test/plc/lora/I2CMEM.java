package ibs.test.plc.lora;

import java.util.ArrayList;
import java.util.List;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.I2C;

@_PLC(comm_code="I2C")
public class I2CMEM extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private List<Float> signal = new ArrayList<Float>();
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal.add(((I2C)signal).val1);
		this.signal.add(((I2C)signal).val2);
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
