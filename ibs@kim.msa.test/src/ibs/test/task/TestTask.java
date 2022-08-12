package ibs.test.task;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ibs.test.down.DownFrame;
import ibs.test.down.Things;
import ibs.test.down.signal.RS485;
import ibs.test.dto.AuthCCTVPack;
import ibs.test.dto.DonwLinkPack;
import ibs.test.dto.JackSonTestPack;
import ibs.test.util.EUIGen;
import v3.venus.route.ADVRouter;

public class TestTask {
	
	public String reqAuthCCTV(AuthCCTVPack pack) throws Exception {
		// 조건문 CCTV인지 아닌지 및  CCTV URL 가져오기 
		
		pack.setKey();
		return pack.enc();
	}

	public JackSonTestPack jackSonTest(JackSonTestPack pack) throws Exception {
		
		pack.ignoreString = "Ignore String";
		pack.jsonIncludeAlways = "always test";
		pack.jsonIncludeNAbsent = "absent String";
		pack.jsonIncludeNDefault = 0;
		pack.jsonIncludeNEmpty = "";
		pack.jsonIncludeCustom = "custom String";
		pack.propertiTest1 = "properti 1 String";
		pack.propertiTest2 = "properti 2 String";
		
		return pack;
	}

	public void downLinkTest(DonwLinkPack pack) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		
		EUIGen gen = new EUIGen().gen2Serial(pack.uuid);
		
		Things things = obj.readValue(obj.writeValueAsBytes(pack.data), RS485.class);
		things.interface_type = Integer.parseInt(gen.dtype1);
		things.sensor_number = Integer.parseInt(gen.dtype2);
		things.encode();
		
		DownFrame frame = new DownFrame();
		frame.pub("application/" + pack.app_id + "/device/" + gen.eui + "/command/down", things.down());
	
		frame.close();
	}
}
