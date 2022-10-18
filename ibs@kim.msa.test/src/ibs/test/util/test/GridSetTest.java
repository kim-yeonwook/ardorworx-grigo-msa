package ibs.test.util.test;

import ibs.test.dto.chirp.DOWN;
import xery.mecury.GridSet;
import xery.mecury.Result;

public class GridSetTest extends GridSet {

	
	private static final class test {
		public static void main(String[] args) {
			try {
				
				DOWN down = new DOWN();
				
				GridSet ds = down.getGridSet();
				
				System.out.println(ds.getStr("AAA"));
				
				Result rs = new Result();
				rs.RESULT_CNT = 4;
				
				rs.set("main", "1");
				rs.set("main", "2");
				rs.set("main", null);
				rs.set("main", "3");
				
				for (int index = 0; index < rs.RESULT_CNT; index++) {
					System.out.println(rs.getStr("main", index));
					if (rs.getStr("main", index) == null) System.out.println("이거");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
