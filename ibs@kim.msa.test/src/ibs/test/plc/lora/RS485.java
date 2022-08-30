package ibs.test.plc.lora;

import ibs.test.plc.MBPLC;
import ibs.test.plc._SIG;

@_SIG(comm_code="RS485")
public class RS485 extends MBPLC {

	private static final int RS485_SIZE = 16;
	
	@Override
	public byte[] setReq() {
		// TODO Auto-generated method stub
		byte[] _req = new byte[MBAP_SIZE + RS485_SIZE];
		int index = 0;

		System.arraycopy(transaction_id, 0, _req, index, 2);
		index += 2;
		
		System.arraycopy(protocol_id, 0, _req, index, 2);
		index += 2;
		
		int length = MEM_LENGTH + RS485_SIZE;
		byte[] leng = new byte[2];
		
		
		System.arraycopy(transaction_id, 0, _req, index, 2);
		index += 2;
		
		System.arraycopy(unit_id, 0, _req, index, 1);
		index++;

		
		
		return _req;
	}
}
