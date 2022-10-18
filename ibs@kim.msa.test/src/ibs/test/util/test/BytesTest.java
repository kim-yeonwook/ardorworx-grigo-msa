package ibs.test.util.test;

public class BytesTest {
	public final static int BIG_ENDIAN = 99;
	public final static int LITTLE_ENDIAN = 100;
	public final static int BIT_SIZE = 8;
	public final static int INT_BYTE = 4;
	public final static int SHORT_BYTE = 2;
	public final static int LONG_BYTE = 4;
	public final static int FLOAT_BYTE = 4;
	public final static int DOUBLE_BYTE = 8;
	
	public static String binaryToDec(int value) {
		
		return null;
	}
	
	public static String byte2Hex(byte[] value) {
		int size = value.length;
		String hex = "";
		for (int index = 0; index < size; index++) {
			hex += String.format("%02X", value[index] & 0xff);
		}
		return hex;
	}

	public static String byte2HexPad(byte[] value) {
		int size = value.length;
		String hex = "";
		for (int index = 0; index < size; index++) {
			hex += String.format("%02X", value[index] & 0xff) + " ";
		}
		return hex;
	}
	
	public static byte[] chgEndian(byte[] value) {
		int size = value.length;
		byte[] end = new byte[size];
		
		for (int index = 0; index < size; index++) {
			end[index] = (byte)(value[size - (index + 1)] & 0xff);
		}
		return end;
	}
	
	public static byte[] chg2bEndian(byte[] value) {
		int size = value.length;
		byte[] end = new byte[size];
		
		for(int index = 0; index < size; index+=2) {
			System.arraycopy(value, size - (index + 2), end, index, 2);
		}
		
		return end;
	}
	
	public static int byte2int(byte[] value) {
		return byte2intL(value);
	}
	
	public static int byte2int(byte[] value, int is_endian) {
		return is_endian == BIG_ENDIAN ? byte2intB(value) : byte2intL(value);
	}
	
	public static short byte2short(byte[] value) {
		return byte2shortL(value);
	}

	public static short byte2short(byte[] value, int is_endian) {
		return is_endian == BIG_ENDIAN ? byte2shortB(value) : byte2shortL(value);
	}
	
	public static long byte2long(byte[] value) {
		return byte2longL(value);
	}

	public static long byte2long(byte[] value, int is_endian) {
		return is_endian == BIG_ENDIAN ? byte2longB(value) : byte2longL(value);
	}
	
	public static float byte2float(byte[] value) {
		return byte2floatL(value);
	}

	public static float byte2float(byte[] value, int is_endian) {
		return is_endian == BIG_ENDIAN ? byte2floatB(value) : byte2floatL(value);
	}
	
	public static double byte2double(byte[] value) {
		return byte2doubleL(value);
	}
	
	public static double byte2double(byte[] value, int is_endian) {
		return is_endian == BIG_ENDIAN ? byte2doubleB(value) : byte2doubleL(value);
	}
	
	private static int byte2intB(byte[] value) {
		int intValue = 0x00;
		
		for (int index = 0; index < INT_BYTE; index++) {
			intValue |= (value[index] & 0xff) << (BIT_SIZE * index);
		}
		
		return intValue;
	}
	
	private static int byte2intL(byte[] value) {
		int intValue = 0x00;
		
		for (int index = 0; index < INT_BYTE; index++) {
			intValue |= (value[INT_BYTE - (index + 1)] & 0xff) << (BIT_SIZE * index);
		}
		
		return intValue;
	}

	private static short byte2shortB(byte[] value) {
		short shortValue = 0x00;
		
		for (int index = 0; index < shortValue; index++) {
			shortValue |= (value[index] & 0xff) << (BIT_SIZE * index);
		}
		
		return 0;
	}
	
	private static short byte2shortL(byte[] value) {
		short shortValue = 0x00;
		
		for (int index = 0; index < SHORT_BYTE; index++) {
			shortValue |= (value[SHORT_BYTE - (index + 1)] & 0xff) << (BIT_SIZE * index);
		}
		
		return shortValue;
	}
	
	private static long byte2longB(byte[] value) {
		return 0;
	}

	private static long byte2longL(byte[] value) {
		long longValue = 0x00;
		
		for (int index = 0; index < LONG_BYTE; index++) {
			longValue |= (long)(value[LONG_BYTE - (index + 1)] & 0xff) << (BIT_SIZE * index);
		}
		
		return longValue;
	}
	
	private static float byte2floatB(byte[] value) {
		return 0f;
	}
	
	private static float byte2floatL(byte[] value) {
		int floatValue = 0x00;
		
		for (int index = 0; index < FLOAT_BYTE; index++) {
			floatValue |= (value[FLOAT_BYTE - (index + 1)] & 0xff) << (BIT_SIZE * index);
		}
		
		return Float.intBitsToFloat(floatValue);
	}
	
	private static double byte2doubleB(byte[] value) {
		return 0;
	}
	
	private static double byte2doubleL(byte[] value) {
		long doubleValue = 0x00;
		
		for (int index = 0; index < DOUBLE_BYTE; index++) {
			doubleValue |= (long)(value[DOUBLE_BYTE - (index + 1)] & 0xff) << (BIT_SIZE * index);
		}
		
		return Double.longBitsToDouble(doubleValue);
	}
	
	public static byte[] int2byte(int value) {
		return int2byteL(value);
	}

	public static byte[] int2byte(int value, int is_endian) {
		return is_endian == BIG_ENDIAN ? int2byteB(value) : int2byteL(value);
	}
	
	public static byte[] short2byte(short value) {
		return short2byteL(value);
	}

	public static byte[] short2byte(short value, int is_endian) {
		return is_endian == BIG_ENDIAN ? short2byteB(value) : short2byteL(value);
	}
	
	public static byte[] long2byte(long value) {
		return long2byteL(value);
	}

	public static byte[] long2byte(long value, int is_endian) {
		return is_endian == BIG_ENDIAN ? long2byteB(value) : long2byteL(value);
	}
	
	public static byte[] float2byte(float value) {
		return float2byteL(value);
	}

	public static byte[] float2byte(float value, int is_endian) {
		return is_endian == BIG_ENDIAN ? float2byteB(value) : float2byteL(value);
	}
	
	public static byte[] double2byte(double value) {
		return double2byteL(value);
	}

	public static byte[] double2byte(double value, int is_endian) {
		return is_endian == BIG_ENDIAN ? double2byteB(value) : double2byteL(value);
	}
	
	private static byte[] int2byteB(int value) {
		return null;
	}
	
	private static byte[] int2byteL(int value) {
		byte[] intbyte = new byte[INT_BYTE];
		for (int index = 0 ; index < INT_BYTE; index++) {
			intbyte[INT_BYTE - (index + 1)] = (byte)((value >> (index * BIT_SIZE)) & 0xff);
		}
		return intbyte;
	}
	
	private static byte[] short2byteB(short value) {
		return null;
	}
	
	private static byte[] short2byteL(short value) {
		byte[] shortbyte = new byte[SHORT_BYTE];
		for (int index = 0; index < SHORT_BYTE; index++) {
			shortbyte[SHORT_BYTE - (index + 1)] = (byte)((value >> (index * BIT_SIZE)) & 0xff);
		}
		return shortbyte;
	}
	
	private static byte[] long2byteB(long value) {
		return null;
	}
	
	private static byte[] long2byteL(long value) {
		byte[] longbyte = new byte[LONG_BYTE];
		for (int index = 0; index < LONG_BYTE; index++) {
			longbyte[LONG_BYTE - (index + 1)] = (byte)((value >> (index * BIT_SIZE)) & 0xff);
		}
		return longbyte;
	}
	
	private static byte[] float2byteB(float value) {
		return null;
	}
	
	private static byte[] float2byteL(float value) {
		byte[] floatbyte = new byte[FLOAT_BYTE];
		int floatValue = Float.floatToIntBits(value);
		for (int index = 0; index < FLOAT_BYTE; index++) {
			floatbyte[FLOAT_BYTE - (index + 1)] = (byte)((floatValue >> (index * BIT_SIZE)) & 0xff);
		}
		return floatbyte;
	}
	
	private static byte[] double2byteB(double value) {
		return null;
	}
	
	private static byte[] double2byteL(double value) {
		byte[] doublebyte = new byte[DOUBLE_BYTE];
		long doubleValue = Double.doubleToLongBits(value);
		for (int index = 0; index < DOUBLE_BYTE; index++) {
			doublebyte[DOUBLE_BYTE - (index + 1)] = (byte)((doubleValue >> (index * BIT_SIZE)) & 0xff);
		}
		return doublebyte;
	}
	
	
	private static final class test {
		public static void main(String[] args) throws Exception {
			
//			byte[] h = Base64Coder.dec2Str("");
//			System.out.println(BytesTest.byte2HexPad("6fh79TqiX9zbh0AQEr15yFMqZ9aZRDDf".getBytes("UTF-8")));
			
//			byte[] l = Base64Coder.dec2Str("BAFhAGLsdT5B2wNDQjryi2LsdXpB2pBrQjuVDA==");
//			System.out.println(BytesTest.byte2HexPad(l));
//			
//			byte[] m = Base64Coder.dec2Str("AwMAAGLsdY9B1P0vYux1y0HV3+Q=");
//			System.out.println(BytesTest.byte2HexPad(m));
//
//			byte[] g = Base64Coder.dec2Str("BQJhAGLseCgAAwAEAAQABAPmABUBEgGAYux4ZAADAAMAAwADA+4AGwESAYA=");
//			System.out.println(BytesTest.byte2HexPad(g));
//
//			int test_int = BytesTest.INT_BYTE + BytesTest.SHORT_BYTE + BytesTest.LONG_BYTE + BytesTest.FLOAT_BYTE + BytesTest.DOUBLE_BYTE;
//			byte[] test = new byte[test_int];
//			
//			int index = 0;
//			
//			byte[] intByte = BytesTest.int2byte(1111, BytesTest.LITTLE_ENDIAN);
//			System.arraycopy(intByte, 0, test, index, BytesTest.INT_BYTE);
//			index += BytesTest.INT_BYTE;
//			
//			byte[] shortByte = BytesTest.short2byte((short)2227, BytesTest.LITTLE_ENDIAN);
//			System.arraycopy(shortByte, 0, test, index, BytesTest.SHORT_BYTE);
//			index += BytesTest.SHORT_BYTE;
//			
//			
//			byte[] longByte = BytesTest.long2byte((long)3331, BytesTest.LITTLE_ENDIAN);
//			System.arraycopy(longByte, 0, test, index, BytesTest.LONG_BYTE);
//			index += BytesTest.LONG_BYTE;
//			
//			byte[] floatByte = BytesTest.float2byte(4449.4f, BytesTest.LITTLE_ENDIAN);
//			System.arraycopy(floatByte, 0, test, index, BytesTest.FLOAT_BYTE);
//			index += BytesTest.FLOAT_BYTE;
//			
//			
//			byte[] doubleByte = BytesTest.double2byte(5552.55, BytesTest.LITTLE_ENDIAN);
//			System.arraycopy(doubleByte, 0, test, index, BytesTest.DOUBLE_BYTE);
//			index += BytesTest.DOUBLE_BYTE;
//			
//			////////////////////////////////////////////////////////////////////////////////////
//			
//			test = BytesTest.chgEndian(test);
//			test = BytesTest.chgEndian(test);
//			
//			System.out.println(BytesTest.byte2Hex(test));
//			System.out.println(BytesTest.byte2HexPad(test));
//			
////			try {
////				byte[] base64 = Base64Coder.enc2byte(test);
////				
//////				for (int index3 = 0; index3 < base64.length; index3++) {
//////					System.out.print(Integer.toHexString(base64[index3] & 0xff));
//////				}
////				System.out.println(new String(base64));
////			} catch (Exception e) {
////				// TODO: handle exception
////			}
////			System.out.println();
////			try {
////				
////			test = Base64Coder.dec2Str("AAAEVwizAAANA0WLCzNAtbCMzMzMzQ==");
////			} catch (Exception e) {
////				// TODO: handle exception
////			}
//			
//			index = 0;
//			
//			
//			byte[] pIntByte = new byte[BytesTest.INT_BYTE];
//			System.arraycopy(test, index, pIntByte, 0, BytesTest.INT_BYTE);
//			System.out.println(BytesTest.byte2int(pIntByte, BytesTest.LITTLE_ENDIAN));
//			index += BytesTest.INT_BYTE;
//			
//			byte[] pShortByte = new byte[BytesTest.SHORT_BYTE];
//			System.arraycopy(test, index, pShortByte, 0, BytesTest.SHORT_BYTE);
//			System.out.println(BytesTest.byte2short(pShortByte, BytesTest.LITTLE_ENDIAN));
//			index += BytesTest.SHORT_BYTE;
//			
//			byte[] pLongByte = new byte[BytesTest.LONG_BYTE];
//			System.arraycopy(test, index, pLongByte, 0, BytesTest.LONG_BYTE);
//			System.out.println(BytesTest.byte2long(pLongByte, BytesTest.LITTLE_ENDIAN));
//			index += BytesTest.LONG_BYTE;
//
//			byte[] pFloatByte = new byte[BytesTest.FLOAT_BYTE];
//			System.arraycopy(test, index, pFloatByte, 0, BytesTest.FLOAT_BYTE);
//			System.out.println(BytesTest.byte2float(pFloatByte, BytesTest.LITTLE_ENDIAN));
//			index += BytesTest.FLOAT_BYTE;
//			
//			byte[] pDoubleByte = new byte[BytesTest.DOUBLE_BYTE];
//			System.arraycopy(test, index, pDoubleByte, 0, BytesTest.DOUBLE_BYTE);
//			System.out.println(BytesTest.byte2double(pDoubleByte, BytesTest.LITTLE_ENDIAN));
//			index += BytesTest.DOUBLE_BYTE;
//			
////			BytesTest test = new BytesTest();
////			System.out.println("BYTE2HEX : ");
////			for (int index = 0; index < test.BYTE2HEX.length; index++) {
////				System.out.println(test.BYTE2HEX[index]);
////			}
////			System.out.println();
////			
////			System.out.println("BYTE2HEX_PADDING : ");
////			for (int index = 0; index < test.BYTE2HEX_PAD.length; index++) {
////				System.out.println(test.BYTE2HEX_PAD[index]);
////			}
////			System.out.println();
////			
////			System.out.println("BYTE2HEX_PADDING : ");
////			for (int index = 0; index < test.HEXPADDING.length; index++) {
////				System.out.println(test.HEXPADDING[index]);
////			}
////			System.out.println();
////			
////			System.out.println("BYTE2HEX_PADDING : ");
////			for (int index = 0; index < test.BYTEPADDING.length; index++) {
////				System.out.println(test.BYTEPADDING[index]);
////			}
////			System.out.println();
////			
////			System.out.println("BYTE2CHAR : ");
////			for (int index = 0; index < test.BYTE2CHAR.length; index++) {
////				System.out.println(test.BYTE2CHAR[index]);
////			}
////			System.out.println();
//			
////			byte[] a = BytesTest.int2byte(85491);
////			System.out.println(a.length);
////			System.out.println(BytesTest.byte2int(a));
////
////			System.out.println();
////			byte[] b = BytesTest.short2byte((short)31515);
////			System.out.println(b.length);
////			System.out.println(BytesTest.byte2short(b));
////			
////			System.out.println();
////			byte[] c = BytesTest.long2byte((long)813515);
////			System.out.println(c.length);
////			System.out.println(BytesTest.byte2long(c));
////			
////			System.out.println();
////			byte[] d = BytesTest.float2byte((float)76515.159);
////			System.out.println(d.length);
////			System.out.println(BytesTest.byte2float(d));
////			
////			System.out.println();
////			byte[] e = BytesTest.double2byte((double)988515.4213);
////			System.out.println(e.length);
////			System.out.println(BytesTest.byte2double(e));
////			System.out.println(ByteBuffer.wrap(e, 0, 8).order(ByteOrder.BIG_ENDIAN).getDouble());
////			System.out.println(ByteBuffer.wrap(d, 0, 4).order(ByteOrder.BIG_ENDIAN).getFloat());
//			
////			byte test_v2 = (byte)5000;
////			System.out.println(test_v2 & 0xff);
////			byte test_v3 = (byte)120;
////			System.out.println(test_v3);
//			
////			byte[] unsigned_int = BytesTest.int2byte(1111, BytesTest.LITTLE_ENDIAN);
////			System.out.println(Byte.toUnsigned(unsigned_int));
//			
//			System.out.println("##########################################################################");
////			byte[] ven = Base64Coder.dec2Str("BQMAAGLPzksAAAcvAAAAAAAAAAAAAAAA");
//			byte[] ven_a = new byte[4];
//			System.arraycopy(ven, 0, ven_a, 0, 4);
//			
//			System.out.println((int)ven_a[0]);
//			System.out.println((int)ven_a[1]);
//			System.out.println((int)ven_a[2]);
//			System.out.println((int)ven_a[3]);
//
//			byte[] ven_b = new byte[20];
//			System.arraycopy(ven, 4, ven_b, 0, 20);
////			byte[] ven_b_endian = BytesTest.chg2bEndian(ven_b);
//			byte[] ven_b_endian = ven_b;
//			
//			int ven_b_index = 0;
//			byte[] time = new byte[4];
//			System.arraycopy(ven_b_endian, ven_b_index, time, 0, 2);
//			ven_b_index += 4;
//			System.out.println(BytesTest.byte2intL(time));
//			
////			byte[] val1 = new byte[2];
////			System.arraycopy(ven_b_endian, ven_b_index, val1, 0, 2);
////			ven_b_index += 2;
////			System.out.println(BytesTest.byte2shortL(val1));
//			byte[] val1 = new byte[4];
//			System.arraycopy(ven_b_endian, ven_b_index, val1, 0, 4);
//			ven_b_index += 4;
//			System.out.println(BytesTest.byte2floatL(val1));
//			
//			byte[] val2 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val2, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val2));
//			
//			byte[] val3 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val3, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val3));
//			
//			byte[] val4 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val4, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val4));
//			
//			byte[] val5 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val5, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val5));
//			
//			byte[] val6 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val6, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val6));
//			
//			byte[] val7 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val7, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val7));
//			
//			byte[] val8 = new byte[2];
//			System.arraycopy(ven_b_endian, ven_b_index, val8, 0, 2);
//			ven_b_index += 2;
//			System.out.println(BytesTest.byte2shortL(val8));
//			
//			
//			
//			
			
		}
	}
}
