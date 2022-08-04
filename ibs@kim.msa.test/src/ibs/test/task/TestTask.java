package ibs.test.task;

import ibs.test.dto.JackSonTestPack;

public class TestTask {

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
}
