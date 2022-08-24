package ibs.test;

import java.util.HashMap;

import ibs.test.dto.AuthCCTVPack;
import ibs.test.dto.DonwLinkPack;
import ibs.test.dto.EdgePack;
import ibs.test.dto.IbsPack;
import ibs.test.dto.JackSonTestPack;
import ibs.test.edge.Edge;
import ibs.test.task.TestTask;
import v3.venus.mod.UriMethod;
import v3.venus.mod._uri;
import v3.venus.route.SvcRouter;

public class KIM {
	
	// RESET
	@_uri(method=UriMethod.PUT, uri="/edge/reset")
	public void resetEdge(EdgePack pack) throws Exception {
		Edge.edge_list.get(pack.USR_SEQ).resetKey(pack);
	}
	
	
	// CCTV 인증?
	@_uri(method=UriMethod.PUT, uri="/node/auth/cctv")
	public String reqAuthCCTV(AuthCCTVPack pack) throws Exception {
		return new TestTask().reqAuthCCTV(pack);
	}
	
	
	@_uri(method=UriMethod.PUT, uri="/node/json/test")
	public JackSonTestPack jackSonTest(JackSonTestPack pack) throws Exception {
		return new TestTask().jackSonTest(pack);
	}

	// 다운 링크 TEST
	@_uri(method=UriMethod.PUT, uri="/node/donwlink")
	public void downLinkTest(DonwLinkPack pack) throws Exception {
		new TestTask().downLinkTest(pack);
	}

	@_uri(method=UriMethod.PUT, uri="/node/smtp")
	public void smtpTest(DonwLinkPack pack) throws Exception {
		new TestTask().smtpTest(pack);
	}
	
	
	
	// 다운 링크 TEST
		@_uri(method=UriMethod.PUT, uri="/node/donwlink22222")
		public void downLinkTes22t(IbsPack pack) throws Exception {
			System.out.println("AFSASFASDASDASDASD");
			SvcRouter.request("test/test", new HashMap<String, Object>(), (topic, msg)->{
				
				System.out.println(msg);
				System.out.println("TETETETETETETETETETT");
			
			});
			for(int i=0;i<20;i++) {
				System.out.println("!!!!!!!!!!!!!!1");
				Thread.sleep(1000);
			}
		}
	
		// 다운 링크 TEST
				@_uri(method=UriMethod.PUT, uri="/test/test")
				public IbsPack downLinkTes2212t(IbsPack pack) throws Exception {
					
					Thread.sleep(1000*11);
					return pack;
				}
}
