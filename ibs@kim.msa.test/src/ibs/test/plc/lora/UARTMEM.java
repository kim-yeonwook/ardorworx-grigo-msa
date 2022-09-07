package ibs.test.plc.lora;

import java.util.ArrayList;
import java.util.List;

import ibs.test.plc.MBPLC;
import ibs.test.plc._PLC;
import ibs.test.signal.SignalIF;
import ibs.test.signal.loras.UART;

@_PLC(comm_code="UART")
public class UARTMEM extends MBPLC {
	
	public String serial_no;
	public String comm_code;
	
	private List<Short> signal = new ArrayList<Short>();
	
	@Override
	public void setSignal(SignalIF signal) {
		// TODO Auto-generated method stub
		this.signal.add(((UART)signal).val1);
		this.signal.add(((UART)signal).val2);
		this.signal.add(((UART)signal).val3);
		this.signal.add(((UART)signal).val4);
		this.signal.add(((UART)signal).val5);
		this.signal.add(((UART)signal).val6);
		this.signal.add(((UART)signal).val7);
		this.signal.add(((UART)signal).val8);
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
