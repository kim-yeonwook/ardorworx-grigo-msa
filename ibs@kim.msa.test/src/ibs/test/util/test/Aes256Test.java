package ibs.test.util.test;

import java.io.OutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.io.Bytes;

// aes256 256bit => 32byte
public class Aes256Test {
	
	private static final byte[] SECRET_KEY = new byte[32];
//			"98123548756842354892154897535789";
	
	public void crtKey() throws Exception {
//		Random rd = new Random();
//		rd.setSeed(System.currentTimeMillis());
//		for(int i=0;i<CBusTicket.iv_keys_size;i++) {
//				for(int j=0;j<CBusTicket.iv_size;j++) {
//				byte a = (byte)rd.nextInt();
//				CBusTicket.ivs[i][j] = a;
//			}
//		}
		Random rd = new Random();
		rd.setSeed(System.currentTimeMillis());
		for(int i=0;i<SECRET_KEY.length;i++) {
			byte a = (byte)rd.nextInt();
			Aes256Test.SECRET_KEY[i] = a;
		}
	}
	
	public static String aes256enc(byte[] key, byte[] plan) throws Exception {
		if(32!=key.length) throw new Exception("key length wrong");
		
		byte[] iv = new byte[16];
        System.arraycopy(key, 0, iv, 0, 16);
		return aes256enc(iv,key,plan);
	}
	public static String aes256enc(byte[] iv, byte[] key, byte[] plan) throws Exception {
		if(32!=key.length||16!=iv.length) throw new Exception("key length wrong");
		
		Key secKey = new SecretKeySpec(key, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secKey, new IvParameterSpec(iv));
        byte[] encrypted = c.doFinal(plan);
        return new String(Base64.getEncoder().encode(encrypted));

	}
	
	public static byte[] aes256dec(byte[] key, String enc) throws Exception {
		if(32!=key.length) throw new Exception("key length wrong");
		
		byte[] iv = new byte[16];
        System.arraycopy(key, 0, iv, 0, 16);
		return aes256dec(iv,key,enc);
	}
	public static byte[] aes256dec(byte[] iv, byte[] key, String enc) throws Exception {
		if(32!=key.length||16!=iv.length) throw new Exception("key length wrong");
	
		Key secKey = new SecretKeySpec(key, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secKey, new IvParameterSpec(iv));
        return c.doFinal(Base64.getDecoder().decode(enc));
	}
	
	public void a() {
	}
	
	private final static class test {
		public static void main(String[] args) {
			try {
				
//				String  a = "AQEJAA4QAAEBAAAAAAAAAAABAAAAAAAAAAABAAAAAAAAAAABAAAAAAAAAAA=";
//				byte[] z = Base64.getDecoder().decode(a);
//				System.out.println(BytesTest.byte2HexPad(z));
				
//				Socket so = new Socket("118.67.142.121", 9000);
//				so.connect(endpoint);
//				System.out.println("aaaaaaaaaaaaaa");
//				OutputStream out = so.getOutputStream();
//				out.close();
//				so.close();
//			Aes256Test aes = new Aes256Test();
			
//			aes.crtKey();
			
//			String e = Bytes.aes256enc(Aes256Test.SECRET_KEY, new ObjectMapper().writeValueAsBytes("안녕하세요"));
//			String z = Bytes.aes256enc(Aes256Test.SECRET_KEY, new ObjectMapper().writeValueAsBytes("안녕하세요"));
			
//			System.out.println(new String(Aes256Test.SECRET_KEY));
//			System.out.println(e);
//			System.out.println(z);
//			System.out.println(e.replace('+', '-').replace('/', '_').replace("=", ""));
//			System.out.println(z.replace('+', '-').replace('/', '_').replace("=", ""));
			
//			System.out.println(new String(aes.aes256dec(Aes256Test.SECRET_KEY, e)));      
//			System.out.println(new String(aes.aes256dec(Aes256Test.SECRET_KEY, e.replace('+', '-').replace('/', '_').replace("=", ""))));      
//			System.out.println(new String(aes.aes256dec(Aes256Test.SECRET_KEY, e.replace('+', '-'))));
//			System.out.println(new String(aes.aes256dec(Aes256Test.SECRET_KEY, e.replace('/', '_'))));
//			System.out.println(new String(aes.aes256dec(Aes256Test.SECRET_KEY, e.replace("=", ""))));
//			System.out.println(new String(aes.aes256dec(Aes256Test.SECRET_KEY, e.replace('+', '-').replace('/', '_').replace("=", ""))));
//					.replace('+', '-').replace('/', '_').replace("=", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
