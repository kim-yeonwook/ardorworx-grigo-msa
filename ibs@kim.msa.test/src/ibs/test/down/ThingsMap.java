package ibs.test.down;

import java.util.HashMap;

import ibs.test.down.signal.ADC;
import ibs.test.down.signal.DI;
import ibs.test.down.signal.DO;
import ibs.test.down.signal.I2C;
import ibs.test.down.signal.RS485;
import ibs.test.down.signal.UART;

public class ThingsMap extends HashMap<String, Class<? extends Things>> {

	public ThingsMap() {
		put("DI", DI.class);
		put("DO", DO.class);
		put("ADC", ADC.class);
		put("I2C", I2C.class);
		put("RS485", RS485.class);
		put("UART", UART.class);
	}
}
