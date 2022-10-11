package ibs.test.util;

import ibs.test.dto.DOWN;
import xery.mecury.GridSet;

public class GridSetTest extends GridSet {

	
	private static final class test {
		public static void main(String[] args) {
			try {
				
				DOWN down = new DOWN();
				
				GridSet ds = down.getGridSet();
				
				System.out.println(ds.getStr("AAA"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
