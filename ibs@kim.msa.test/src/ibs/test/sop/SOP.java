package ibs.test.sop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SOP 정보 > 진행은 Block단위로 진행된다
 *  - 백 플로우는 없다
 *  - 분기도 없음 
 * @author changhee
 *
 */
public class SOP {
	
//////////////////////////////////
// SOP 정보 section start
//
	
	public String code;
	public String name;
	
	
	public boolean isEnd;
	
//
// SOP 정보 section end
//////////////////////////////////
	

	/**
	 * 실행 리스트 
	 */
	public List<SOPBlock> blocks;
	
	public List<Boolean> okList = new ArrayList<Boolean>();
	
	/**
	 * SOP 수행 ?
	 *   용접 작업 SOP <- 스케줄에 따른 용접 작업 발생  
	 * 		1. 작업전 체크리스트 출력 
	 * 		2. 작업전 바닥 청소 
	 * 		3. 작업전 VANTAX(공기질) 센싱값 확인
	 * 		4. 작업전 온도 체크 
	 * 		5. 작업 진행중 시간당 10분씩 휴식
	 * 		6. 작업완료 후 불똥 점검
	 * 		7. 작업완류 후 바닥 청소 
	 * *********************************************
	 * 
	 *   화재 상황 SOP <- 화재 이벤트 생성
	 *   	1. CCTV로 해당 현장 모니터 
	 *   	2. 인력 대피
	 *   	3. 소방활동 
	 * *********************************************
	 * 
	 * 
	 * 
	 * 	블럭단위 자동실행(B.E) X
	 * 	블럭단위로 처리 *******
	 * 
	 * @throws Exception
	 */

	
	//인덱스는 현재 블럭이다.
	//최종 수행된 블럭 인덱스 <- 중간에 종료된 블럭을 찾는 용도로 사용 할 수도 있다 
	protected int currentIndex  = -1;
	public int closeIndex;
	
	public SOPBlock currentBlock;
	public SOPBlock getCurrentBlcok() {
		return this.blocks.get(currentIndex);
		
	}
	
	
	/**
	 * 수행할 블럭의 수행절차 항목을 알려줌 
	 * @return
	 */
	public HashMap<String,Object> getBlockTitle() throws Exception {
		HashMap<String,Object> rtn = new HashMap<String,Object>();
		rtn.put("index", currentIndex);
		
		ArrayList<SOPView> vList = new ArrayList<SOPView>();  
		rtn.put("list", vList);
		for(SOPBlock b: blocks) {
			vList.add(b.view());
		}
		
		return rtn;
	}
	
	
	
	

	
	public void result(SOPBlock block, SOPResult act) throws Exception {
		block.result(act);
		closeIndex = (++currentIndex)-1;
	}
	
	
	
	
	
	private static final class _test {
		public static void main(String[] args) {
			try {
				SOP sop = new SOP();
//				SOP sop = new Obje
				sop.code = "화재 발생 SOP";
				
				sop.getBlockTitle();
				
				
				try {
					SOPBlock block1 = sop.getCurrentBlcok();
					
					SOPResult result1 = new SOPResult();
					result1.result(new HashMap<String, Object>());
					
					sop.result(block1, result1);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
//		public static void main(String[] args) {
//			try {
//				
//				//용접 SOP 수행 테스트 (플로우가 정상적인지? 중간에 끊겨지는게 있는지 확인) 
//				//화면의 흐름대로 진행을 시켜보면 된다 
//				
//				SOP sop  = new SOP();
//				//찾은 용접SOP의 초기화 
//				sop.code = "용접"; //사용자가 작업을 수행하기전에 용접SOP 찾음 
//				
//				
//				//수행 리스트가 화면에 떠야 함
//				//	<- 수행 리스트에 현재시점에 수행한 블럭의 위치(인덱스)
//				/*
//				 * 		1. 작업전 체크리스트 출력 
//				 * 		2. 작업전 바닥 청소 
//				 * 		3. 작업전 VANTAX(공기질) 센싱값 확인
//				 * 		4. 작업전 온도 체크 
//				 * 		5. 작업 진행중 시간당 10분씩 휴식
//				 * 		6. 작업완료 후 불똥 점검
//				 * 		7. 작업완류 후 바닥 청소 
//				*/
//				HashMap<String,Object> list = sop.getBlockTitle();
//
//				SOPBlock block = null;
//				try {
//					//대상 블럭을 선택해서 실제 내용 확인
//					//수행 블럭은 사용자가 선택하는것 , 대상 블럭의 인덱스를 parameter로 받아야 한다
//					block = sop.getCurrentBlcok();
//					
//					//사용자가 SOP 수행완료했고, 근거를 제출 
//					SOPView view = block.view();
//					//사전 작업
//					block.preset();
//					SOPResult act = new SOPResult(); //<- 사용자는 근거 제출시 정상 종료인지, 비정상 종료인지 정리해야 한다
//					act.result(null); // <- 작업자가 블럭 수행후 결과물을 저장해야 한다 (근거 데이타) 
//					
//					
//					sop.result(block, act);
//					sop.okList.add(SOPResult.TYPE_OK == block.type);
//					
//					sop.isEnd = true;
//					
//				}catch(Exception e) {
//					sop.okList.add(false);
//					
//					if(null!= block) {
//						block.type = SOPResult.TYPE_NOK;
//					}
//					
//				}
//				
//
//				
//				SOP sop2  = new SOP();
//				//찾은 화재SOP의 초기화 
//				sop.code = "화재";
//				
//				//수행 리스트가 화면에 떠야 함
//				//	<- 수행 리스트에 현재시점에 수행한 블럭의 위치(인덱스)
//				/*
//				 *   화재 상황 SOP <- 화재 이벤트 생성
//				 *   	1.
//				 *   	2.
//				 *   	3. CCTV로 해당 현장 모니터 (자동으로 CCTV 연결 > 모니터링 화면 팝업) <- BackEnd  ? 	
//				 *   		<- next로 안갈수도 있다... 현재 블럭에서 SOP 종료가 가능하다 
//				 *   	4. 인력 대피
//				 *   	5. 소방활동
//				 *   
//				 *    VS
//				 *    
//				 * 		1. 작업전 체크리스트 출력 
//				 * 		2. 작업전 바닥 청소 
//				 * 		3. 작업전 VANTAX(공기질) 센싱값 확인
//				 * 		4. 작업전 온도 체크 
//				 * 		5. 작업 진행중 시간당 10분씩 휴식
//				 * 		6. 작업완료 후 불똥 점검
//				 * 		7. 작업완류 후 바닥 청소 
//				*/
//				HashMap<String,Object> list2 = sop2.getBlockTitle();
//				
//				SOPBlock block2 = null;
//				try {
//					//대상 블럭을 선택해서 실제 내용 확인
//					//수행 블럭은 사용자가 선택하는것 , 대상 블럭의 인덱스를 parameter로 받아야 한다
//					block2 = sop.getCurrentBlcok();
//					block2.preset();
//					
//					//수행 내용 확인 
//					SOPView view2 = block2.view();
//
//					//CCTV 확인해봤더니 오탐 이더라...
//					String comment = "================"; //중지 사유
//					SOPResult act2 = new SOPResult(); //<- 사용자는 근거 제출시 정상 종료인지, 비정상 종료인지 정리해야 한다
//					act2.result(null); // <- 작업자가 블럭 수행후 결과물을 저장해야 한다 (근거 데이타) 
//					act2.close(comment);
//					
//					sop.result(block2, act2);
//					sop.okList.add(SOPResult.TYPE_OK == block.type);
//					
//					boolean isClose = block2.type == SOPResult.TYPE_CLOSE; 
//					if(isClose) {
//						sop.isEnd = true;
//						//어느단계에서 멈췄냐????? <-- currentIndex와 lastIndex가 동일한 경우는 처리할 필요없다
//						
//						return;
//					}
//
//				}catch(Exception e) {
//					sop.okList.add(false);
//					if(null!= block) {
//						block.type = SOPResult.TYPE_NOK;
//					}
//					
//				}
//				
//				SOPBlock block3 = null;
//				try {
//					//대상 블럭을 선택해서 실제 내용 확인
//					//수행 블럭은 사용자가 선택하는것 , 대상 블럭의 인덱스를 parameter로 받아야 한다
//					block3 = sop.getCurrentBlcok();
//					block3.preset();
//					
//					//사용자가 SOP 수행완료했고, 근거를 제출 
//					SOPView view3 = block.view();
//					//호출측에서 타이틀 출력 
//					view3.title();
//					//호출측에서 내용 출력 
//					view3.contents();
//					//호출측에서 결과물 등록 표현 
//					view3.view();
//					
//					
//					
//					SOPResult act3 = new SOPResult(); //<- 사용자는 근거 제출시 정상 종료인지, 비정상 종료인지 정리해야 한다 
//					act3.result(null); // <- 작업자가 블럭 수행후 결과물을 저장해야 한다 (근거 데이타) 
//
//					
//					sop.result(block3, act3);
//					sop.okList.add(SOPResult.TYPE_OK == block.type);
//					
//					sop.isEnd = true;
//					
//				}catch(Exception e) {
//					sop.okList.add(false);
//					
//					if(null!= block) {
//						block.type = SOPResult.TYPE_NOK;
//					}
//					
//				}
//		}
	
	}
}
