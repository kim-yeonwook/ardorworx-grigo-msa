package ibs.test.rest.cs;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.dto.chirp.AuthPack;
import ibs.test.dto.chirp.DevicePack;
import ibs.test.dto.chirp.GateWayPack;
import v3.venus.mod.UriMethod;

public class ChirpStack {
	
	protected ObjectMapper mapper;
	protected String jwt;
	
	public ChirpStack() throws Exception {
		mapper = new ObjectMapper();
		login();
	}
	
	protected void login() throws Exception {
		try {
			ChirpStackRestApiSender api = new ChirpStackRestApiSender();
			api.method = UriMethod.POST.method();
			api.url = "/api/internal/login";
			api.send(mapper.writeValueAsBytes(new AuthPack()));
			this.jwt = (String)api.getRtn().get("jwt");
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void RegistDevice(DevicePack pack) throws Exception {
		try {
			ChirpStackRestApiSender api = new ChirpStackRestApiSender();
			api.method = UriMethod.POST.method();
			api.url = "/api/devices";
			api.jwt = jwt;
			api.send(mapper.writerWithView(DevicePack.JACKSONVIEWS.DEVICE.class).withRootName("device").writeValueAsBytes(pack));
			
			api = new ChirpStackRestApiSender();
			api.method = UriMethod.POST.method();
			api.url = "/api/devices/"+pack.devEUI+"/keys";
			api.jwt = jwt;
			api.send(mapper.writerWithView(DevicePack.JACKSONVIEWS.APPKEY.class).withRootName("deviceKeys").writeValueAsBytes(pack));
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void RegistGateWay(GateWayPack pack) throws Exception {
		try {
			ChirpStackRestApiSender api = new ChirpStackRestApiSender();
			api.method = UriMethod.POST.method();
			api.url = "/api/gateways";
			api.jwt = jwt;
			api.send(mapper.writeValueAsBytes(pack));
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
