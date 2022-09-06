package ibs.test.action;

import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.edge.Edge;
import ibs.test.plc.MBPLC;
import ibs.test.plc.PMap;
import ibs.test.signal.SignalIF;
import ibs.test.signal.SignalMap;
import mecury.io.Bytes;
import mecury.log.Log;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route.AdvertizedBus.Callback;
import v3.venus.route._ADVAction;

@_ADVAction
public class SignalUpActionToPLC implements ADVAction {
	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {				
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				HashMap<String, Object> map = obj.readValue(body, HashMap.class);
				String MSG = (String)map.get("MSG");
				int seed = Bytes.int2hex(MSG.substring(MSG.length()-2));
				MSG = MSG.substring(0,MSG.length()-2).replace('-', '+').replace('_', '/');
				byte[] plan = Bytes.aes256dec(Edge.edge_list.get((int)map.get("USR_SEQ")).key(seed), MSG);
				
				SignalIF signal = obj.readValue(plan, SignalMap.get((String)map.get("COMM_CODE")));
				
				MBPLC plc = obj.readValue(plan, PMap.get(signal.getComm_code()));
				
				
			}catch(Exception e) {
				Log.err(Modular.ID, "SIGNAL UP ACTION ERR", e);
			}
		};
	}

	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/edge/signal/up1";
	}
}
