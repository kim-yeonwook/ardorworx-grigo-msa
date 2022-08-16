package ibs.test.action;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.util.ApiMap;
import mecury.log.Log;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route._ADVAction;
import v3.venus.route.AdvertizedBus.Callback;

@_ADVAction
public class ApiListAction implements ADVAction {

	@Override
	public Callback action() {
		// TODO Auto-generated method stub
		return (topic, body) -> {
			try {
				ObjectMapper obj = new ObjectMapper();
				obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

				HashMap<String, Object> map = obj.readValue(body, HashMap.class);
				
				ArrayList<String> apiList = (ArrayList<String>)map.get("list");
				if (apiList != null) {
					for (int index = 0; index < apiList.size(); index++) {
						if(!ApiMap.apiList.contains(apiList.get(index))) ApiMap.apiList.add(apiList.get(index));
					}
				}
				
				ArrayList<String> depList = (ArrayList<String>)map.get("deprecated");
				if (depList != null) {
					for (int index = 0; index < depList.size(); index++) {
						if(!ApiMap.depList.contains(depList.get(index))) ApiMap.depList.add(depList.get(index));
					}
				}
				
				System.out.println();
				System.out.println("---------------------------------------------------------------------------------");
				
				System.out.println(topic);
				System.out.println();
				System.out.println("API LIST : " + ApiMap.apiList);
				System.out.println("DEP LIST : " + ApiMap.depList);
				
				System.out.println("---------------------------------------------------------------------------------");
				
				
				
			} catch (Exception e) {
				Log.err(Modular.ID, "API LIST ACTION ERR", e);
			}
		};
	}
	
	@Override
	public String topic() {
		// TODO Auto-generated method stub
		return "ADV/api/lists";
	}
	
}
