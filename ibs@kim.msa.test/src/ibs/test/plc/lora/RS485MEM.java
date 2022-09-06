package ibs.test.plc.lora;

import java.util.ArrayList;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.RS485;
import ibs.test.util.BytesTest;

@_PLC(comm_code="LORA.CS.RS485")
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
		RS485 s = (RS485)signal;
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
	public byte[] setReq() {
		// TODO Auto-generated method stub
		byte[] _req = new byte[MBAP_SIZE + RS485_SIZE];
		int index = 0;

		System.arraycopy(TRANSACTION, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		System.arraycopy(PROTOCOL, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] leng = setLength();
		System.arraycopy(leng, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		System.arraycopy(unit_id, 0, _req, index, 1);
		index++;
		
		byte f_code = (byte)(function_code & 0xff);
		System.arraycopy(f_code, 0, _req, index, 1);
		index++;
		
		byte[] start_address = new byte[BytesTest.SHORT_BYTE];
		start_address[0] = (byte)(this.start_address>>8 & 0xff);
		start_address[1] = (byte)(this.start_address & 0xff);
		
		for (int a = 0; a < this.signal.size(); a++) {
			byte[] val = new byte[BytesTest.SHORT_BYTE];
			val = BytesTest.short2byte(this.signal.get(a));
			System.arraycopy(val, 0, _req, index, BytesTest.SHORT_BYTE);
			index += BytesTest.SHORT_BYTE;
		}
		
		return _req;
	}
	
	protected byte[] setLength() {
		int leng = MEM_LENGTH + RS485_SIZE; 
		byte[] length = new byte[BytesTest.SHORT_BYTE];
		int size = length.length;
		for (int index = 0; index < size; index++) {
			length[size-(index+1)] = (byte)((leng>>8*index)&0xff);
		}
		return length;
	}
}
