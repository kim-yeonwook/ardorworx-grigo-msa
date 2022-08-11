package ibs.test.down.signal;

import ibs.test.down.Things;

public class RS485 extends Things {
	
	public int baudrate;
	
	public int data_length;
	
	public int stop_bits;
	
	public int flow_control;
	
	public int parity;
	
	public int slave_id;
	
	public int value1_fcode;
	
	public int value1_size;
	
	public short value1_addr;
	
	public int value2_fcode;
	
	public int value2_size;
	
	public short value2_addr;
	
	public int value3_fcode;
	
	public int value3_size;
	
	public short value3_addr;
	
	public int value4_fcode;
	
	public int value4_size;
	
	public short value4_addr;
	
	
	@Override
	public String encode_value() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
