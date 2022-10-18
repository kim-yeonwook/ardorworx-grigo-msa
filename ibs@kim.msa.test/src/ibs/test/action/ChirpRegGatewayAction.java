package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ibs.test.dto.EdgePack;
import ibs.test.dto.chirp.GateWayPack;
import ibs.test.edge.Edge;
import ibs.test.rest.cs.ChirpStack;
import ibs.test.util.ProcessManager;
import mecury.io.Bytes;
import mecury.log.Log;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class ChirpRegGatewayAction implements ADVAction {
	
	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				obj.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
				
				HashMap<String, Object> param = obj.readValue(body, HashMap.class);
				String MSG = (String)param.get("MSG");
				int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
				MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
				byte[] plan = Bytes.aes256dec(Edge.edge_list.get((int)param.get("USR_SEQ")).key(seed), MSG);
				
				GateWayPack pack = obj.readValue(plan, GateWayPack.class);
				
				new ChirpStack().RegistGateWay(pack);
				
				ProcessManager process = new ProcessManager();
				process.setCommand("mosquitto_passwd").setCommand("-b").setCommand("/etc/mosquitto/passwd").setCommand(pack.id).setCommand("'!"+pack.id+"!'");
				
				process.send();
				
			} catch (Exception e) {
				Log.info(Modular.ID, "Regist Gateway Fail");
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/edge/255/gateway";
	}
}
