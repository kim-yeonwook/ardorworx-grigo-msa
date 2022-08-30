package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import ibs.test.util.BytesTest;

public abstract class MBPLC {

	public enum Function {
		empty(1,"EMPY", 1),
		abc(2, "", 10001),
		level1(4,"LVL1", 30001),
		read(3,"FULL", 40001),level2(6,"LVL2", 30001),level3(16,"LVL3", 30001)
		,;
		
		private int id;
		private String purpose;
		private int address;
		
		private Function(int id, String purpose, int address) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.purpose = purpose;
			this.address = address;
		}
	}
	
	public final static int MBAP_SIZE = 7;
	
	public final static int READ = 99;
	public final static int WRITE = 100;
	
	public static final int MEM_LENGTH = 2;
	
	public int status;

	// 2 byte
	public byte[] transaction_id = {0x00, 0x00};

	// MODBUS-TCP는 0x0000
	public byte[] protocol_id = {0x00, 0x00};

	// 2 byte
	public byte[] length;

	// TCP 통신 0x01로 고정
	public byte unit_id = 0x01;

	public int function_code;
	

	// 시작 메모리는 0으로 시작하지 않음 (1 ~ 9999, 10001 ~ 19999, 30001 ~ 40001, 40001 ~ 49999)
	// 0으로 지정하면 메모리는 1로잡힘 (예 : start_address를 50으로 지정하면 메모리는 51로 잡힘)
	public int start_address;

	public int end_address;

	
	public abstract byte[] setReq();
	
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
