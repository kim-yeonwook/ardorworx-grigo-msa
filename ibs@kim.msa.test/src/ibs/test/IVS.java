package ibs.test;

import java.util.Random;

import mecury.io.LocalProperties;

public final class IVS {
	
	private final static int iv_size = 32; //32byte
	private final static int iv_keys_size = 4;
	private final static byte[][] ivs = new byte[iv_keys_size][iv_size];
	
	
	private final static byte[][] IVS = {
			"K234567890123456789!@#$456789012".getBytes(),
			"z23456789%$^&45678cvdm@45678901!".getBytes(),
			"u23456789iouF45678901()&^678901H".getBytes(),
			"L2GdjkAV90!@%$^^789012345678901$".getBytes()
	};
	private static byte[][] IVS_VENDOR = {
			"1234567890$%FHDFG$#$%^#@$%78901!".getBytes(),
			"1QCVBVG%@#FG3456789012345678901H".getBytes(),
			"#$FBCVFGH@123456789012345678901k".getBytes(),
			"NFKEXNVD$VFDGB$%^DV012345678901)".getBytes()
	};
	
	static {
		//iv key realtime set
		if("Y".equals(LocalProperties.get("seed.realtime","N"))) {
			Random rd = new Random();
			rd.setSeed(System.currentTimeMillis());
			for(int i=0;i<iv_keys_size;i++) {
				for(int j=0;j<iv_size;j++) {
					ivs[i][j] = (byte)rd.nextInt();
				}
			}
			
		} else {
			System.arraycopy(IVS, 0, ivs, 0, iv_keys_size);
		}
	}
	
	
	public static byte[] get_iv(int index) {
		return ivs[index%4];
	}

	public static byte[] get_iv_vendor(int index) {
		return IVS_VENDOR[index%4];
	}
	
	

	

}
