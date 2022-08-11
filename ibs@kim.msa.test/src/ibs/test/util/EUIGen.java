package ibs.test.util;

import java.util.Arrays;

import mecury.io.Bytes;

public class EUIGen {
	public static final String[] MAP_NODE_TYPE = {"BA","PW"};
	public static final String[] MAP_FRQ = {"K","C","U","E","I","R"};
	public static final String[] MAP_DTYPE = {"0","1","2","3","4","5","6","7","8","9"
												,"A","B","C","D","E","F","G","H","I","J","K","L"
												,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static final String[] MAP_CODE = {"DI","DO","ADC","I2C","RS485","UART"};

	public static final byte APP_UP = (byte)0x0a;
	public static final byte APP_DOWN = (byte)~APP_UP;
	
	
	public String serial;
	public String eui;
	public String app_key;
	
	public char node_type_val;
	public char[] frq_plan_val;
	public char[] dtype1_val;
	public char[] dtype2_val;
	public char[] yy_val;
	public char[] mm_val;
	public char[] dd_val;
	public char[] num_val;
	
	public String node_type_code;
	public String frq_plan_code;
	public String dtype1_code;
	public String dtype2_code;
	public String yy_code;
	public String mm_code;
	public String dd_code;
	public String num_code;
	
	public String node_type;
	public String frq_plan;
	public String dtype1;
	public String dtype2;
	public String yy;
	public String mm;
	public String dd;
	public String num;
	
	
	public EUIGen gen2Serial(String serailNo) throws Exception {
		this.serial = serailNo;
		char[] i = this.serial.toCharArray();
		
		node_type = String.valueOf(new char[]{i[0],i[1]});
		frq_plan = String.valueOf(i[3]);
		dtype1 = String.valueOf(i[4]);
		dtype2 = String.valueOf(i[5]);
		yy = yy_code = String.valueOf(new char[]{i[7],i[8]});
		mm = String.valueOf(new char[]{i[9],i[10]});
		dd = dd_code = String.valueOf(new char[]{i[11],i[12]});
		num = num_code = String.valueOf(new char[]{i[14],i[15],i[16]});
		
		node_type_code = String.valueOf((char)(Arrays.asList(MAP_NODE_TYPE).indexOf(node_type) + 'b'));
		frq_plan_code = String.format("%x", (Arrays.asList(MAP_FRQ).indexOf(frq_plan) * 2) + 0xf0);
//		dtype1_code = String.format("%02x", Integer.parseInt(dtype1, 16));
		dtype1_code = String.format("%02x", 0xff & Arrays.asList(MAP_DTYPE).indexOf(dtype1));
//		dtype2_code = String.format("%x", 0xff - Integer.parseInt(dtype2, 16));
		dtype2_code = String.format("%x", 0xff - Arrays.asList(MAP_DTYPE).indexOf(dtype2));
		mm_code = String.format("%x", Integer.parseInt(mm, 16));;

		char[] value = (node_type_code + frq_plan_code + dtype2_code + dtype1_code + yy_code + mm_code + dd_code + num_code).toCharArray();
		
		StringBuilder build = new StringBuilder("f");
		
		build.append(value[7]);
		build.append(value[8]);
		build.append(value[0]);
		build.append(value[9]);
		build.append(value[1]);
		build.append(value[10]);
		build.append(value[11]);
		build.append(value[2]);
		build.append(value[14]);
		build.append(value[3]);
		build.append(value[4]);
		build.append(value[13]);
		build.append(value[5]);
		build.append(value[12]);
		build.append(value[6]);

		this.eui = build.toString();
		
		return this;
	}
	
	
	public EUIGen gen2EUI(String eui) {
		this.eui = eui;
		char[] i = this.eui.toCharArray();
		
		node_type_val = i[3];
		frq_plan_val = new char[]{i[5],i[8]};
		dtype1_val = new char[]{i[13],i[15]};
		dtype2_val = new char[]{i[10],i[11]};
		yy_val = new char[]{i[1],i[2]};
		mm_val = new char[]{i[4]};
		dd_val = new char[]{i[6],i[7]};
		num_val = new char[]{i[14],i[12],i[9]};
		
		node_type_code = String.valueOf(node_type_val);
		frq_plan_code = String.valueOf(frq_plan_val);
		dtype1_code = String.valueOf(dtype1_val);
		dtype2_code = String.valueOf(dtype2_val);
		yy = yy_code = String.valueOf(yy_val);
		mm_code = String.valueOf(mm_val);
		dd = dd_code = String.valueOf(dd_val);
		num = num_code = String.valueOf(num_val);
		
		node_type = String.valueOf(MAP_NODE_TYPE[node_type_val - 'b' ]);
		frq_plan  = MAP_FRQ[(Bytes.int2hex(frq_plan_code) - 0xf0) / 2];

		dtype1 = MAP_DTYPE[Bytes.int2hex(dtype1_code)];
		dtype2 = MAP_DTYPE[0xff - Bytes.int2hex(dtype2_code)];
		mm = String.format("%02d", Bytes.int2hex("0" + mm_code));
		
		this.serial = node_type +"-" + frq_plan + dtype1 + dtype2 +"-"+yy +mm +dd+ "-"+num; 

		return this;
	}
	
	public String toSerialCode() {
		return node_type_code +"-" + frq_plan_code +"-"+ dtype1_code +"-"+dtype2_code +"-"+yy_code +"-"+mm_code +"-"+dd_code+ "-"+num_code; 
	}
	public String toCode() {
		return MAP_CODE[Bytes.int2hex(dtype1_code)-1];
	}
	
	public byte[] toAppKey() {
		
		byte[] b = hexStringToByteArray2(eui);
		byte[] key = new byte[b.length];

		for(int index=0;index<key.length;index++) {
			byte bdown = (byte)(b[index]&(byte)0x0f);
			key[index] = (byte)((bdown & APP_UP) << 4 | (bdown & APP_DOWN));
			
		}
		
		return key;
	}
	

	
	public byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	public byte[] hexStringToByteArray2(String s) {
	    int len = s.length();
	    byte[] data = new byte[len];
	    for (int i = 0; i < len; i++) {
	        data[i] = (byte) (Character.digit(s.charAt(i), 16));
	    }
	    return data;
	}
	
	
	
	@SuppressWarnings("unused")
	private static final class _test {

		public static void main(String[] args) {
			try {
				
				
				String eui = "f22b4f0106ff0006";
				System.out.println(eui);
				EUIGen gen = new EUIGen().gen2EUI(eui);
				
				String serial = gen.serial;
				System.out.println(serial);

				gen.gen2Serial(serial);
				eui = gen.eui;
				System.out.println(eui);

				byte[] key = gen.toAppKey();
				System.out.println(Bytes.byte2hexstring(key));
				

				
				
				gen.eui = "F20B6F1001FF000A";

				byte[] b = gen.hexStringToByteArray2(gen.eui);
				key = new byte[b.length];
				
				byte up = (byte)0x0a;
				byte down = (byte)~up;
				
				byte bup = (byte)((b[0]&(byte)0xf0) >> 4);
				
				for(int index=0;index<key.length;index++) {
					byte bdown = (byte)(b[index]&(byte)0x0f);
					key[index] = (byte)((bdown & up) << 4 | (bdown&down));
					
				}
				
				System.out.println(Bytes.hexDump("test", key));
				System.out.println(Bytes.byte2hexstring(key));
				
				//A52000A124A501000001A5A5000000A0
				//A52000A124A501000001A5A5000000A0
				
				
				
				
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
