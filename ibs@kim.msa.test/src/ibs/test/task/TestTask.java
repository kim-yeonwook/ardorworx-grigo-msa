package ibs.test.task;

import java.util.ArrayList;

import ibs.test.dto.DonwLinkPack;
import ibs.test.dto.test.AuthCCTVPack;
import ibs.test.dto.test.JackSonTestPack;
import ibs.test.mail.GmailSend;
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
	
		ADVRouter.pub("ADV/down/signal", pack.encode());
	}
	
	public void smtpTest(DonwLinkPack pack) throws Exception {
		GmailSend g = new GmailSend();
		
		ArrayList<String> e_mail = new ArrayList<String>();
		e_mail.add("dsudnr96@naver.com");
		g.send(e_mail, "타이틀", "메세지");
	}
}
