package ibs.test;

import java.sql.Connection;

import ibs.test.dto.IbsPack;
import mecury.io.LocalProperties;
import xen.mecury.JdbcPool;
import xen.mecury.Xen;

public class MariaPool extends JdbcPool {
	
	
	public static Connection getConnectio(IbsPack pack) throws Exception {
		
		return Xen.getConnection(LocalProperties.get(pack.version + ".db.pool.name"));
		
	}

	
	public static Connection getConnectio(String version) throws Exception {
		
		return Xen.getConnection(LocalProperties.get(version + ".db.pool.name"));
		
	}
	
	public static Connection getConnectio() throws Exception {
		
		return Xen.getConnection(LocalProperties.get("v1.db.pool.name"));
		
	}
	

}
