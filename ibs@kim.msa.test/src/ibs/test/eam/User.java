package ibs.test.eam;

import mecury.io.Bytes;
import mecury.io.LocalProperties;

public class User {
	
	public static final int HEADER_SIZE = Bytes.LONG_BYTE+ Bytes.INT_BYTE+ Bytes.INT_BYTE+ Bytes.INT_BYTE+ Bytes.INT_BYTE+ 1; 
	
	public long a_expired;
	public long g_expired;
	
	public String CNT_CD;
	
	public int USR_SEQ;
	public int USR_TYPE;
	public int ROLE;

	
	public User() {
		a_expired = LocalProperties.get("token.a.expired")==null?Long.MAX_VALUE:Long.parseLong(LocalProperties.get("token.a.expired"));
		g_expired = LocalProperties.get("token.g.expired")==null?Long.MAX_VALUE:Long.parseLong(LocalProperties.get("token.g.expired"));
	}

	
	
	public String access_token(int index) throws Exception {
		
		int total = HEADER_SIZE;
		
		
		byte[] enc = new byte[total];
		int offset = 0;
		
		//만료시간 : 8
		byte[] time = Bytes.long2byte(System.currentTimeMillis()+a_expired);
		System.arraycopy(time, 0, enc, offset, Bytes.LONG_BYTE);
		offset += Bytes.LONG_BYTE;
		
		//CNT_CD : 4
		System.arraycopy(CNT_CD.getBytes(), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;
		
		//USR_SEQ : 4
		System.arraycopy(Bytes.int2byte(USR_SEQ), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;
		
		//USR_TYPE : 4
		System.arraycopy(Bytes.int2byte(USR_TYPE), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;

		//ROLE : 4
		System.arraycopy(Bytes.int2byte(ROLE), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;
		
		//seed : 1
		String e = Bytes.aes256enc(IVS.get_iv(index), enc)
					.replace('+', '-').replace('/', '_').replace("=", "");

		return e + Bytes.byte2hex(index);
	}
	
	
	public byte[] read_access(String e) throws Exception {

		int seed = Bytes.int2hex(e.substring(e.length()-2));
		e = e.substring(0,e.length()-2).replace('-', '+').replace('_', '/');
		byte[] d = Bytes.aes256dec(IVS.get_iv(seed), e);
		
		int offset = 0;
		byte[] item = new byte[Bytes.LONG_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.LONG_BYTE);
		a_expired = Bytes.byte2long(item);
		offset += Bytes.LONG_BYTE;
		
		item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		CNT_CD = new String(item);
		offset += Bytes.INT_BYTE;
		
		item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		USR_SEQ = Bytes.byte2int(item);
		offset += Bytes.INT_BYTE;

		item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		USR_TYPE = Bytes.byte2int(item);
		offset += Bytes.INT_BYTE;
		
		item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		ROLE = Bytes.byte2int(item);
		offset += Bytes.INT_BYTE;
		
		return d;
	}
	
	
	
	
	
	
	public String grant_token(int index) throws Exception{

		int total = Bytes.INT_BYTE+ Bytes.LONG_BYTE+ Bytes.INT_BYTE+ Bytes.INT_BYTE+ 1;
		byte[] enc = new byte[total];
		
		int offset = 0;
		
		//ROLE : 4
		System.arraycopy(Bytes.int2byte(ROLE), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;
		
		//만료시간 : 8
		byte[] time = Bytes.long2byte(System.currentTimeMillis()+g_expired);
		System.arraycopy(time, 0, enc, offset, Bytes.LONG_BYTE);
		offset += Bytes.LONG_BYTE;
		
		//CNT_CD : 4
		System.arraycopy(CNT_CD.getBytes(), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;
		
		//USR_SEQ : 4
		System.arraycopy(Bytes.int2byte(USR_SEQ), 0, enc, offset, Bytes.INT_BYTE);
		offset += Bytes.INT_BYTE;
		
		//seed : 1
		String e = Bytes.aes256enc(IVS.get_iv(index), enc)
					.replace('+', '-').replace('/', '_').replace("=", "");

		return e + Bytes.byte2hex(index);
		
	}
	
	public byte[] read_grant(String e) throws Exception {

		int seed = Bytes.int2hex(e.substring(e.length()-2));
		e = e.substring(0,e.length()-2).replace('-', '+').replace('_', '/');
		byte[] d = Bytes.aes256dec(IVS.get_iv(seed), e);
		
		int offset = 0;
		byte[] item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		ROLE = Bytes.byte2int(item);
		offset += Bytes.INT_BYTE;
		
		item = new byte[Bytes.LONG_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.LONG_BYTE);
		g_expired = Bytes.byte2long(item);
		offset += Bytes.LONG_BYTE;
		
		item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		CNT_CD = new String(item);
		offset += Bytes.INT_BYTE;
		
		item = new byte[Bytes.INT_BYTE];
		System.arraycopy(d, offset, item, 0, Bytes.INT_BYTE);
		USR_SEQ = Bytes.byte2int(item);
		offset += Bytes.INT_BYTE;

		return d;
	}
	
	
	
	
	


	@SuppressWarnings("unused")
	private static final class _test {
		public static void main(String[] args) {
			try {
				
				User c = new User();
				
				c.USR_SEQ = 700;
				c.USR_TYPE = 8;
				c.CNT_CD = "SEJN";
				c.ROLE = 12;
				
				int seed = (int)(Math.random()*100);
				String token = c.access_token(seed);
				System.out.println(token);

				String rtoken = c.grant_token(seed);
				System.out.println(rtoken);
				

				String enc = token;
//				enc = "z9KX4DBl7ryJen4X-u9NC47qTBZkyP8vANdWwphKjbs28";
//				enc = "dC9RAKziljxQffZ_GoEwOy3fcHONQu-gzbLo4X9BToI4d";

//-- CYbcc1_Sa4cr-hYQ49UY-dLyRU7-zVGCj_9r_sTwmJQ3f
//-- qXuoOp608HwEn5Gfz5Vv4tNiA6KZI01_5rovKbwQb9I3f
				System.out.println(enc);
				
				c = new User();
				c.read_access(enc);

				System.out.println(c.USR_SEQ);
				System.out.println(c.USR_TYPE);
				System.out.println(c.CNT_CD);
				System.out.println(c.ROLE);
				System.out.println(c.a_expired);

				c = new User();
				c.read_grant(rtoken);
				System.out.println(c.USR_SEQ);
				System.out.println(c.CNT_CD);
				System.out.println(c.ROLE);
				System.out.println(c.a_expired);
				

				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
