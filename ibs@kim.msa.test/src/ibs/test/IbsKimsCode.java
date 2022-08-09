package ibs.test;

import mecury.code.Code;

public enum IbsKimsCode implements Code {

	db_dupkey("500201",500,"Key Dup err",2),db_notkey("500202",500,"Key not found",2),db_notparent("500203",500,"parent not found",2),db_use_data("500204",500,"Data used foreign",2),db_notExist("500205",500,"Data not exist",2),db_constraint("500206",500,"Constraint Violation",2),
	
	err_data_logic("500301",500,"Data Logic Err",2),
	
	dup_uuid("500401",500,"해당 UUID가 사용되고 있습니다."),foreign_del_device_site("500402", 500, "해당 장비가 현장에 설치 되어 있어서 삭제 할수 없습니다."),foreign_del_device_his("500402", 500, "해당 장비에 이벤트가 발생해 삭제 할수 없습니다."),
	
	param_check_err("500901",500,"입력값이 잘못 됐습니다",2),
	;
	;
	
	private int state;
	private String code;
	private String desc;
	public int action = 1;
	
	private IbsKimsCode(String code, int state, String desc) {
		this.state = state;
		this.code = code;
		this.desc = desc;
	}
	private IbsKimsCode(String code, int state, String desc, int action) {
		this.state = state;
		this.code = code;
		this.desc = desc;
		this.action = action;
	}
	
	@Override
	public int status() {
		// TODO Auto-generated method stub
		return state;
	}
	
	@Override
	public String code() {
		// TODO Auto-generated method stub
		return code;
	}
	
	@Override
	public String desc() {
		// TODO Auto-generated method stub
		return desc;
	}
	
	@Override
	public int action() {
		// TODO Auto-generated method stub
		return action;
	}
	
	@Override
	public Code setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	
	public Code setAction(int action) {
		this.action = action;
		return this;
	}

}
