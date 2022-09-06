package ibs.test.plc.lora;

import java.util.HashMap;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.UART;

@_PLC(comm_code="LORA.CS.UART")
public class UARTMEM extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private UART signal;
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal = (UART)signal;
	}
	
	@Override
	public byte[] setReq(byte[] _req) {
		// TODO Auto-generated method stub
		return _req;
	}
	
	@Override
	public int dataLength() {
		// TODO Auto-generated method stub
		return 0;
	}
}
