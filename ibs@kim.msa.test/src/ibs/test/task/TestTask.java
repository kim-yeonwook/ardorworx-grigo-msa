package ibs.test.task;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.down.DownFrame;
import ibs.test.down.Things;
import ibs.test.down.ThingsMap;
import ibs.test.dto.AuthCCTVPack;
import ibs.test.dto.DonwLinkPack;
import ibs.test.dto.JackSonTestPack;
import ibs.test.dto.LoRaDevicePack;
import ibs.test.mail.GmailSend;
import ibs.test.util.EUIGen;

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
		
		ThingsMap map = new ThingsMap();
		
		Things things = obj.readValue(obj.writeValueAsBytes(pack.data), map.get(gen.toCode()));
		things.interface_type = Integer.parseInt(gen.dtype1);
		things.sensor_number = Integer.parseInt(gen.dtype2);
		things.encode();
		
		DownFrame frame = new DownFrame();
		frame.pub("application/" + pack.app_id + "/device/" + gen.eui + "/command/down", things.down());
	
		frame.close();
	}
	
	public void InsertLoRaDeviceIntoCS(LoRaDevicePack pack) throws Exception {
		pack.serialNo = "BA-K52-260904-004";
		EUIGen gen = new EUIGen().gen2Serial(pack.serialNo);
		pack.devEUI = gen.eui;
		pack.applicationID = "6";
		pack.deviceProfileID = "28d0d1ad-7b6a-47c5-b117-14dbbf15443d";
		pack.name = "TEST_DEVICE_00";
		pack.description = "TEST_DEVICE_DESC_00";
	}
	
	public void smtpTest(DonwLinkPack pack) throws Exception {
		GmailSend g = new GmailSend();
		
		ArrayList<String> e_mail = new ArrayList<String>();
		e_mail.add("dsudnr96@naver.com");
		g.send(e_mail, "타이틀", "메세지");
	}
}
