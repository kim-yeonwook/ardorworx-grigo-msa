package ibs.test.plc.lora;

import java.util.ArrayList;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.RS485;
import ibs.test.util.BytesTest;

@_PLC(comm_code="RS485")
public class RS485MEM extends MBPLC {

	private static final int RS485_SIZE = 16;
	
	public String serial_no;
	public String comm_code;
	
//	private RS485 signal;
	
	private ArrayList<Short> signal = new ArrayList<Short>();
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
//		this.signal = (RS485)signal;
		this.signal.add(((RS485)signal).val1);
		this.signal.add(((RS485)signal).val2);
		this.signal.add(((RS485)signal).val3);
		this.signal.add(((RS485)signal).val4);
		this.signal.add(((RS485)signal).val5);
		this.signal.add(((RS485)signal).val6);
		this.signal.add(((RS485)signal).val7);
		this.signal.add(((RS485)signal).val8);
	}
	
	@Override
	public byte[] setReq(byte[] _req) {
		int index = 13;
		
		for (int size = 0; size < this.signal.size(); size++) {
			byte[] val = new byte[WORD_SIZE];
			val = BytesTest.short2byte(this.signal.get(size));
			System.arraycopy(val, 0, _req, index, WORD_SIZE);
			index += WORD_SIZE;
		}
		
		return _req;
	}
	
	@Override
	public int dataLength() {
		return WORD_SIZE + RS485_SIZE;
	}
	
}
