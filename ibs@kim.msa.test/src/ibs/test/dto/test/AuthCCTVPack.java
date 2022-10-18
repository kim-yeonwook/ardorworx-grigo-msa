package ibs.test.dto.test;

import ibs.test.dto.IbsPack;
import mecury.io.Bytes;

public class AuthCCTVPack extends IbsPack {
	
	public String url = "https://cctv.ardorworx.co.kr/vod1.m3u8";
	
	public byte[] key = new byte[32];
	
	public String c_url;
	
	public void setKey() throws Exception {
		String z = "12345678123456781234567812345678";
		for (int index=0; index < z.length(); index++) {
			key[index] = (byte)z.charAt(index);
		}
	}
	
	public String enc() throws Exception {
		return Bytes.aes256enc(key, url.getBytes("UTF-8")).replace('+', '-').replace('/', '_').replace("=", "");
	}

	public byte[] dec(String url) throws Exception {
		return Bytes.aes256dec(key, url);
	}
	
	private final static class test {
		public static void main(String[] args) {
			try {
				AuthCCTVPack pack = new AuthCCTVPack();
				pack.setKey();
				System.out.println(new String(pack.key));
				System.out.println(pack.enc());
				System.out.println(new String(pack.dec(pack.enc())));
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
