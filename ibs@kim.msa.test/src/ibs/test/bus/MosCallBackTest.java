package ibs.test.bus;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import mecury.log.Log;
import v3.api.venus.APIProxy;
import v3.api.venus.route.MosCallback;

public abstract class MosCallBackTest extends MosCallback {
	
	
//	@Override
//	public void connectionLost(Throwable arg0) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
		String[] tps = token.getTopics();
		for(String tp:tps) {
			Log.debug(APIProxy.ID, "-------------> " + tp);
		}
	}
}
