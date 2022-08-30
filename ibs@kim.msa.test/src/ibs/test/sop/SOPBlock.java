package ibs.test.sop;

import java.util.HashMap;
import java.util.List;

import ibs.test.sop.view.NoResultView;

/**
 * SOP 실행 항목 
 * @author changhee
 *
 */
public class SOPBlock {

	public HashMap<String,Object> result_data;
	
	
	/**
	 * 정상처리 완료, 비정상 처리 완료, SOP 종료 
	 */
	public int type;
	
	
	public String sopCode;
	public String blcokCode;
	
	public List<SOPAction> action_list;
	
	
	//종료처리 사유 
	public String close_comment;
	
	
	/**
	 * 
	 * SOP를 하는 이유?
	 * 		- 안전한 작업 / 상황에 대한 해소 
	 * 		- 빵가기 싫어서 > 니가 SOP를 수행했다는 근거를 내놔 (사용자들한테 요구) > 나는 근거를 가지고 있어 
	 * 			> 나는 빵에 안가도 돼... 
	 * 			-> 각 수행블럭에서 수행한 내용을 데이타 화 해야 한다 
	 * 
	 * SOP 수행 ?
	 *   용접 작업 SOP blcok list
	 * 		1. 작업전 체크리스트 출력
	 * 			--> "용접 작업전에 작업자 명수만큼 '용접 처리전 개인 확인' 체크리스트를 출력하여, 작업자에게 전달후, 사인을 받아 회수 하세요' 
	 *   
	 *   
	 * 		2. 작업전 바닥 청소 
	 * 		3. 작업전 VANTAX(공기질) 센싱값 확인
	 * 		4. 작업전 온도 체크 
	 * 		5. 작업 진행중 시간당 10분씩 휴식
	 * 				//~ term 시간적인 > 5번의 근거제출 후 6번의 실행 시점이 단절되어 있다. 
	 * 		6. 작업완료 후 불똥 점검
	 * 		7. 작업완류 후 바닥 청소
	 * 
	 *  1. presentation (표현) > 사용자가 해야할 일을 알려줘야 한다 > 핸드폰, 웹 
	 *  2. action (수행) ????
	 *  3. 수행에 대한 결과를 근거로 남기는 행위(behaivor)
	 * 
	 * *********************************************
	 * 
	 *   	1. CCTV로 해당 현장 모니터 (자동으로 CCTV 연결 > 모니터링 화면 팝업) <- BackEnd  ? 	
	 *   		<- next로 안갈수도 있다... 현재 블럭에서 SOP 종료가 가능하다
	 *   		<- 이럴경우 중간 블럭에서 종료 처리한 근거가 남아야 한다 
	 *   		<- SOPAction에 남길수도 있고 VS SOPBlock에 남길수도 있다 
	 *   		<- action()을 살릴거냐? <-- 변경이 일어남!, action()은 그대로 두고 새로운 메쏘드를 만들거냐?  
	 *   	2. 인력 대피
	 *   	3. 소방활동
	 * 
	 * 
	 * 	블럭단위 자동실행(B.E) X
	 * 	블럭단위로 처리 *******
	 * 
	 * @throws Exception
	 */
	
	
	//안전 절차 진입전 사전 활동 내역
	//이 행위는 off로 대처 가능하지만, 향후 이 행위에 대해 실제 로직이 발생 할수도 있으니
	//일단 behaivor로 만들어놓고 이후에 필요한경우에 내용을 확장 > 현재는 공 메쏘드 
	public void preset() throws Exception {
		
	}
	
	
	public SOPView view() throws Exception {
		
		return new NoResultView();
	}
	
	//수행에 대한 결과를 근거로 남기는 행위(behaivor)
	public void result(SOPResult result) throws Exception {

		this.result_data = result.result;
		
		this.type = result.type;
		int test = 1;
		
		//상황별로 분기가 필요
		switch(type) {

		//정상 완료일때 
		case SOPResult.TYPE_OK : {
			okend(test);
			break;
		}
		
		//비정상 완료일때 
		case SOPResult.TYPE_NOK : {
			nokend(test);
			break;
		}

		//SOP 종료일때 
		case SOPResult.TYPE_CLOSE : {

			closeend(test,result);
			break;
		}
		
		}
		
		

	}
	
	
	protected void okend(int test) throws Exception {
		
		test++;
	}
	protected void nokend(int test) throws Exception {
		test--;
	}
	protected void closeend(int test, SOPResult result) throws Exception {
		test = 0;
		this.close_comment = result.close_comment; 
	}

	
	
	
	
	
	
	

}
