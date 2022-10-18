package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.dto.chirp.DevicePack;
import ibs.test.edge.Edge;
import ibs.test.rest.cs.ChirpStack;
import mecury.io.Bytes;
import mecury.log.Log;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class ChirpRegDeviceAction implements ADVAction {

	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {
				try {
					ObjectMapper obj = new ObjectMapper();
					obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					
					HashMap<String, Object> param = obj.readValue(body, HashMap.class);
					String MSG = (String)param.get("MSG");
					int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
					MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
					byte[] plan = Bytes.aes256dec(Edge.edge_list.get((int)param.get("USR_SEQ")).key(seed), MSG);
					
					DevicePack pack = obj.readValue(plan, DevicePack.class);
					pack.decode();
				
					new ChirpStack().RegistDevice(pack);
					
				} catch (Exception e) {
					Log.err(Modular.ID, "Regist Device Fail", e);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/edge/255/node";
	}
	
}
