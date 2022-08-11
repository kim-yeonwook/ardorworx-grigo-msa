package ibs.test.task;

import ibs.test.dto.AuthCCTVPack;
import ibs.test.dto.DonwLinkPack;
import ibs.test.dto.JackSonTestPack;

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
		
	}
}
