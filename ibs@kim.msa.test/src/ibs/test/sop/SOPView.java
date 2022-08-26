package ibs.test.sop;


/**
 * 각 블럭 수행시 사용자들에게 해야 할 수행내용을 표현 해주는 넘 ...
 * 
 * 작업자가 SOP 블럭 수행후 수행 근거를 처리하는 방식에 따라 화면 표현 방식이 분리됨 <- 상속의 이유 
 * 
 * @author changhee
 *
 */
public interface SOPView {
	
	//1. 타이틀(요약) 내용 표현
	String title();
	
	//2. 구체적 내용 표현 
	String contents();
	
	//3. 수행 근거를 처리하는 방식에 따라 화면 표현
	Object view();
}
