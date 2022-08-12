package ibs.test.down.signal;

import ibs.test.down.Things;
import ibs.test.util.BytesTest;
import mecury.io.Bytes;

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
	public byte[] encode_value(byte[] value) throws Exception {
		// TODO Auto-generated method stub
		int index = START_INDEX_SIZE;
		
		value[index] = (byte)baudrate;
		index++;
		
		value[index] = (byte)data_length;
		index++;
		
		value[index] = (byte)stop_bits;
		index++;
		
		value[index] = (byte)flow_control;
		index++;
		
		value[index] = (byte)parity;
		index++;
		
		value[index] = (byte)slave_id;
		index++;
		
		value[index] = (byte)value1_fcode;
		index++;
		
		value[index] = (byte)value1_size;
		index++;
		
		byte[] value1_addr = BytesTest.short2byte(this.value1_addr);
		System.arraycopy(value1_addr, 0, value, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		value[index] = (byte)value2_fcode;
		index++;
		
		value[index] = (byte)value2_size;
		index++;
		
		byte[] value2_addr = BytesTest.short2byte(this.value2_addr);
		System.arraycopy(value2_addr, 0, value, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		value[index] = (byte)value3_fcode;
		index++;
		
		value[index] = (byte)value3_size;
		index++;
		
		byte[] value3_addr = BytesTest.short2byte(this.value3_addr);
		System.arraycopy(value3_addr, 0, value, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		value[index] = (byte)value4_fcode;
		index++;
		
		value[index] = (byte)value4_size;
		index++;
		
		byte[] value4_addr = BytesTest.short2byte(this.value4_addr);
		System.arraycopy(value4_addr, 0, value, index, BytesTest.SHORT_BYTE);
		index += BytesTest.SHORT_BYTE;
		
		return value;
	}
}
