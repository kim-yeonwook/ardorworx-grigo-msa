package ibs.test.dto.test;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ibs.test.dto.IbsPack;

public class UserDetailPack extends IbsPack {
	public String user_id;
	@JsonInclude(Include.NON_EMPTY) public String user_pw;
	
	@JsonInclude(Include.NON_EMPTY) public String user_name;
	@JsonInclude(Include.NON_EMPTY) public String user_nick;
	@JsonInclude(Include.NON_EMPTY) public String user_email;
	@JsonInclude(Include.NON_EMPTY) public String user_hp;
	
	public int user_role = -1;
	public String user_role_name;

	public int user_seq=-1;

	public int user_login_type;
	
	@JsonInclude(Include.NON_EMPTY)public ArrayList<HashMap<String,String>> ext_infos = new ArrayList<HashMap<String,String>>();
	@JsonInclude(Include.NON_EMPTY)public HashMap<String,String> ext_info = new HashMap<>();
	
	public int is_use;
	
	public String user_desc;
	
	public String last_login;
	public int lock1;
	public int lock2;
	public int lock3;

	public String wrt_dtime;

	public void setExtInfo(String title, String code, String value) {
		HashMap<String,String> info = new HashMap<String,String>();
		ext_infos.add(info);
		
		info.put("title", title);
		info.put("code", code);
		info.put("value", value);
	}

	@Override
	public void decode(HashMap<String,Object> param) throws Exception {
		super.decode(param);
		
		if(this.USR_TYPE==-999) this.USR_TYPE=0;
	}
}
