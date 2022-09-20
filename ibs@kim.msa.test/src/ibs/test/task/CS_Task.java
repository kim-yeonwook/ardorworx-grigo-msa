package ibs.test.task;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.csrest.CS_REST;
import ibs.test.csrest.cs.APPKEY;
import ibs.test.csrest.cs.INDEVICE;
import ibs.test.csrest.cs.JWTTOKEN;
import ibs.test.dto.CS_DELIVERY;

public class CS_Task {
	
	protected CS_DELIVERY delivery;
	
	public CS_Task() {}
	
	public CS_Task(CS_DELIVERY delivery) throws Exception {
		delivery.decode();
		this.delivery = delivery;
	}
	
	// jwt token 생성
	public String jwt() throws Exception {
		CS_REST jwt = new JWTTOKEN();
		jwt.send(new ObjectMapper().writerWithView(CS_DELIVERY.JACKSONVIEW.AUTH.class).writeValueAsBytes(this.delivery));
			
		System.out.println((String)jwt.getRtn().get("jwt"));
		return (String)jwt.getRtn().get("jwt");
	}
	
	public void RegistCS(String jwt) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		
		// insert device
		CS_REST node = new INDEVICE();
		node.jwt = jwt;
		node.send(obj.writerWithView(CS_DELIVERY.JACKSONVIEW.DEVICE.class).withRootName("device").writeValueAsBytes(this.delivery));
		
		System.out.println(node.getRtn());
		
		// setting app key
		CS_REST appKey = new APPKEY();
		appKey.jwt = jwt;
		appKey.eui = this.delivery.devEUI;
		appKey.send(obj.writerWithView(CS_DELIVERY.JACKSONVIEW.APPKEY.class).withRootName("deviceKeys").writeValueAsBytes(this.delivery));
		
		System.out.println(appKey.getRtn());
	}
	
}
