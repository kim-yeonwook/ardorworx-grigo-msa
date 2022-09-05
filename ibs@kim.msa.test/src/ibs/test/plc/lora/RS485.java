package ibs.test.plc.lora;

import java.util.HashMap;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.util.BytesTest;

@_PLC(comm_code="RS485")
public class RS485 extends MBPLC {

	private static final int RS485_SIZE = 16;
	
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
		this.val1 = (short)params.get("val1");
		this.val2 = (short)params.get("val2");
		this.val3 = (short)params.get("val3");
		this.val4 = (short)params.get("val4");
		this.val5 = (short)params.get("val5");
		this.val6 = (short)params.get("val6");
		this.val7 = (short)params.get("val7");
		this.val8 = (short)params.get("val8");
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
		
		byte[] val1 = new byte[BytesTest.SHORT_BYTE];
		val1 = BytesTest.short2byte(this.val1);
		System.arraycopy(val1, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val2 = new byte[BytesTest.SHORT_BYTE];
		val2 = BytesTest.short2byte(this.val2);
		System.arraycopy(val2, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val3 = new byte[BytesTest.SHORT_BYTE];
		val3 = BytesTest.short2byte(this.val3);
		System.arraycopy(val3, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val4 = new byte[BytesTest.SHORT_BYTE];
		val4 = BytesTest.short2byte(this.val4);
		System.arraycopy(val4, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val5 = new byte[BytesTest.SHORT_BYTE];
		val5 = BytesTest.short2byte(this.val5);
		System.arraycopy(val5, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val6 = new byte[BytesTest.SHORT_BYTE];
		val6 = BytesTest.short2byte(this.val6);
		System.arraycopy(val6, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val7 = new byte[BytesTest.SHORT_BYTE];
		val7 = BytesTest.short2byte(this.val7);
		System.arraycopy(val7, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		byte[] val8 = new byte[BytesTest.SHORT_BYTE];
		val8 = BytesTest.short2byte(this.val8);
		System.arraycopy(val8, 0, _req, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
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
