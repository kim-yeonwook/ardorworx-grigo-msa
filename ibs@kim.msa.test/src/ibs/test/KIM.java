package ibs.test;

import ibs.test.dto.EdgePack;
import ibs.test.edge.Edge;
import v3.venus.mod.UriMethod;
import v3.venus.mod._uri;

public class KIM {
	
	
	// RESET
	@_uri(method=UriMethod.PUT, uri="/edge/reset")
	public void resetEdge(EdgePack pack) throws Exception {
		Edge.edge_list.get(pack.USR_SEQ).resetKey(pack);
	}
}
