package ibs.test.csrest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public abstract class HttpHandlerV2 {
	
	public String method;
	
	public String host;
	
	public String api;
	
	public String key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhcyIsImV4cCI6MTY0NzU5NzI4NywiaWQiOjEsImlzcyI6ImFzIiwibmJmIjoxNjQ3NTEwODg3LCJzdWIiOiJ1c2VyIiwidXNlcm5hbWUiOiJhZG1pbiJ9.qrP-X1f4EuCNciOPd-0VP25Lb1IlJiy1CzzdME1LmfE";
 
	public HttpURLConnection con;
	
	public abstract void set() throws Exception;
	
	public abstract String setURL() throws Exception;
	
	public abstract byte[] getPayload() throws Exception;
	
	public void write(DataOutputStream _out) throws Exception {
		byte[] _req = getPayload();
		_out.write(_req);	_out.flush();
	}
	
	public abstract HashMap<String, Object> read(DataInputStream _in) throws Exception;
	
	private static final class test {
		public static void main(String[] args) {
//			BufferedReader in = null;
//			BufferedWriter bw = null;
//			try {
//				// DEVICE 저장 로직 token 변경될수도 있음 401에러 날시 token변경
//				if (false) {
					// DEVICE 등록 DEV_EUI 필수
//					URL obj = new URL("http://dev.ardorworx.co.kr:7005/api/devices"); 
//					HttpURLConnection con = (HttpURLConnection)obj.openConnection();
//					con.setUseCaches(false);
//					con.setDoOutput(true);
//					con.setRequestMethod("POST");
//					con.setRequestProperty("Accept", "*/*");
//					con.setRequestProperty("Content-Type", "application/json; utf-8");
//					con.setRequestProperty("Connection", "keep-alive");
//					con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//					con.setRequestProperty("Grpc-Metadata-Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhcyIsImV4cCI6MTY0NzU5NzI4NywiaWQiOjEsImlzcyI6ImFzIiwibmJmIjoxNjQ3NTEwODg3LCJzdWIiOiJ1c2VyIiwidXNlcm5hbWUiOiJhZG1pbiJ9.qrP-X1f4EuCNciOPd-0VP25Lb1IlJiy1CzzdME1LmfE");
//					
//					Map<String, Object> map = new HashMap<String, Object>();
//					Map<String, Object> device = new LinkedHashMap<String, Object>();
//					map.put("device", device);
//					
//					device.put("applicationID", "3");
//					device.put("description", "node2");
//					device.put("devEUI", DEV_EUI2);
//					device.put("deviceProfileID", DEVICE_PROFILE_ID);
////					device.put("isDisabled", true);
//					device.put("name", "TEST_DEVICE2");
////					device.put("referenceAltitude", 0);
//					device.put("skipFCntCheck", true);
////					device.put("tags", new HashMap<String, Object>());
////					device.put("variables", new HashMap<String, Object>());
//					
//					ObjectMapper obm = new ObjectMapper();
//					String param = obm.writerWithDefaultPrettyPrinter().writeValueAsString(map);
//					
//					// 파라미터 세팅
//					try (OutputStream os = con.getOutputStream()){
//						os.write(param.getBytes("utf-8"), 0, param.getBytes("utf-8").length);
//						os.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//					// 결과값 읽기
//					try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
//						StringBuilder response = new StringBuilder();
//						String responseLine = null;
//						while ((responseLine = br.readLine()) != null) {
//							response.append(responseLine.trim());
//						}
//						System.out.println(response.toString());
//					}
//					
//					// DEVICE API KEY 세팅
//					URL obj2 = new URL("http://dev.ardorworx.co.kr:7005/api/devices/" + DEV_EUI2 + "/keys"); 
//					HttpURLConnection con2 = (HttpURLConnection)obj2.openConnection();
//					con2.setUseCaches(false);
//					con2.setDoOutput(true);
//					con2.setRequestMethod("POST");
//					con2.setRequestProperty("Accept", "*/*");
//					con2.setRequestProperty("Content-Type", "application/json; utf-8");
//					con2.setRequestProperty("Connection", "keep-alive");
//					con2.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//					con2.setRequestProperty("Grpc-Metadata-Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhcyIsImV4cCI6MTY0NzU5NzI4NywiaWQiOjEsImlzcyI6ImFzIiwibmJmIjoxNjQ3NTEwODg3LCJzdWIiOiJ1c2VyIiwidXNlcm5hbWUiOiJhZG1pbiJ9.qrP-X1f4EuCNciOPd-0VP25Lb1IlJiy1CzzdME1LmfE");
//					
//					Map<String, Object> map2 = new HashMap<String, Object>();
//					Map<String, Object> device_key = new HashMap<String, Object>();
//					map2.put("deviceKeys", device_key);
//					device_key.put("nwkKey", API_KEY);
//					device_key.put("devEUI", DEV_EUI2);
//					
//					ObjectMapper obm2 = new ObjectMapper();
//					
//					String param2 = obm2.writerWithDefaultPrettyPrinter().writeValueAsString(map2);
//					
//					// 파라미터 세팅
//					try (OutputStream os = con2.getOutputStream()){
//						os.write(param2.getBytes("utf-8"), 0, param2.getBytes("utf-8").length);
//						os.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//					// 결과값 읽기
//					try (BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream(), "utf-8"))) {
//						StringBuilder response = new StringBuilder();
//						String responseLine = null;
//						while ((responseLine = br.readLine()) != null) {
//							response.append(responseLine.trim());
//						}
//						System.out.println(response.toString());
//					}
//				}
		}
	}
}
