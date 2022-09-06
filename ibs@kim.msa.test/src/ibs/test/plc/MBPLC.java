package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

import ibs.test.signal.SignalIF;
import ibs.test.util.BytesTest;

public abstract class MBPLC {

	public String serial_no;
	public String comm_code;
	
	public static final int MEM_SIZE = 30;
	public static final int MBAP_SIZE = 7;
	public static final int WORD_SIZE = 2;
	
	// 2 byte
	public static final byte[] TRANSACTION = {0x00, 0x00};
	// MODBUS-TCP는 0x0000
	public static final byte[] PROTOCOL = {0x00, 0x00};
	// 2 byte
	public byte[] length;

	// TCP 통신 0x01로 고정
	public byte unit_id = 0x01;
	public byte function_code;
	

	// 시작 메모리는 0으로 시작하지 않음 (1 ~ 9999, 10001 ~ 19999, 30001 ~ 40001, 40001 ~ 49999)
	// 0으로 지정하면 메모리는 1로잡힘 (예 : start_address를 50으로 지정하면 메모리는 51로 잡힘)
	public int start_address;
	
	public abstract void setSignal(SignalIF signal);
	
	public byte[] setReq() {
		
		byte[] _req = new byte[MEM_SIZE];
		int index = 0;

		System.arraycopy(TRANSACTION, 0, _req, index, WORD_SIZE);
		index += WORD_SIZE;
		
		System.arraycopy(PROTOCOL, 0, _req, index, WORD_SIZE);
		index += WORD_SIZE;
		
		int leng = dataLength(); 
		byte[] length = new byte[WORD_SIZE];
		length[0] = (byte)(leng>>8 & 0xff);
		length[1] = (byte)(leng & 0xff);
		System.arraycopy(length, 0, _req, index, WORD_SIZE);
		index += WORD_SIZE;
		
		_req[index++] = (byte)(unit_id);
		
		_req[index++] = (byte)(function_code & 0xff);
		
		byte[] start_address = new byte[WORD_SIZE];
		start_address[0] = (byte)(this.start_address>>8 & 0xff);
		start_address[1] = (byte)(this.start_address & 0xff);
		System.arraycopy(start_address, 0, _req, index, WORD_SIZE);
		index += WORD_SIZE;
		
		return setReq(_req);
	}
	public abstract byte[] setReq(byte[] _req);
	
	public abstract int dataLength();
	
	public void write(DataOutputStream _out) throws Exception {
		byte[] _req = setReq();
		_out.write(_req);	_out.flush();
	}
	
	
	public void read(DataInputStream _in) throws Exception {
		byte[] size = new byte[MBAP_SIZE];
		
		
		byte[] _res = new byte[MBAP_SIZE];
		
		_in.read(_res);
		
		System.out.println(BytesTest.byte2HexPad(_res));
	}
	
}
