package ibs.test.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import v3.venus.dto.Pack_;
import xery.mecury.GridSet;
import xery.mecury.Result;

public class IbsPack extends Pack_ {
	
	@JsonIgnore public String CNT_CD;
	@JsonIgnore public int USR_SEQ;
	@JsonIgnore public int USR_TYPE;
	@JsonIgnore public int ROLE;

	@JsonInclude(Include.NON_NULL) public Integer _size;
	@JsonInclude(Include.NON_NULL) public Integer _index;

	@Override
	public void decode(HashMap<String,Object> param) throws Exception {
		// TODO Auto-generated method stub
		this.CNT_CD = (String)param.get("CNT_CD");
		this.USR_SEQ = (int)param.get("USR_SEQ");
		this.USR_TYPE = (int)param.get("USR_TYPE");
		this.ROLE = (int)param.get("ROLE");
	}
	
	
	
	@JsonIgnore 
	public GridSet getGridSet() throws Exception{
		GridSet ds = new GridSet();
		Field[] fs = this.getClass().getFields();
		for(Field f:fs) {
			String fName = f.getName();
			ds.set(fName, f.get(this));
		}
		return ds;
	}
	
	
	@SuppressWarnings("unchecked")
	@JsonIgnore 
	public <T extends IbsPack> T setResult(Result rs, int index) {

		for(String key:rs.getTitles()) {
			try {
				Field f = this.getClass().getField(key);
				Object o = rs.get(key,index);
				if(null!=o) f.set(this, o);
			}catch(Exception e) {}
		}
		return (T)this;
	}
	
	
	public <T extends IbsPack> ResultListPack<T> createListPack() {
		ResultListPack<T> list = new ResultListPack<T>();
		list._result = new ArrayList<T>();
		list.copy(this);
		
		list._size = this._size;
		list._index = this._index;
		
//		try {
//			Field f = this.getClass().getField("_size");
//			list._size = (Integer)f.get(this);
//		}catch(Exception e) {e.printStackTrace();}
//		try {
//			Field f = this.getClass().getField("_index");
//			list._index = (Integer)f.get(this);
//		}catch(Exception e) {e.printStackTrace();}

		return list;
	}


	
	
	

}
