package ibs.test.sop;

import java.util.HashMap;

/**
 * 사용자가 각 블럭 실행한 결과 X, 근거 및 종료처리 
 * 
 * 했다, 안했다... 
 * 
 * @author changhee
 *
 */
public class SOPResult {

/////////////////////////////////////////////////////////
//	
// 블럭 수행후 상태 section 
//	
	public int type;
	public static final int TYPE_OK = 0;
	public static final int TYPE_NOK = 1;
	public static final int TYPE_CLOSE = -1;
	

	public String close_comment;
	public HashMap<String,Object> result;
//	public HashMap<String, SOPAction> result;
	
	
	//정상 처리후 근거처리 
	public void ok() throws Exception {
		type = TYPE_OK;
		
	}
	
	//현장에서 블럭을 수행 완료하지 못하는 경우 
	public void nok() throws Exception {
		type = TYPE_NOK;

	}
	
	
	//SOP 종료사유 기입 
	public void close(String close_comment) throws Exception {
		
		this.close_comment = close_comment;
		type = TYPE_CLOSE;
		
	}

	
	
	
/////////////////////////////////////////////////////////
//
//	블럭 수행후 근거 내용 
//
	
	
	public void result(HashMap<String,Object> result) throws Exception {
		
		
		
		
		this.result = result;
	}
	
	

}
