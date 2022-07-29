package ibs.test.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import xery.mecury.GridSet;


public class ResultListPack<T extends IbsPack> extends IbsPack {
	
	public ArrayList<T> _result;
	@JsonInclude(Include.NON_NULL) public Integer _total_cnt;
	@JsonInclude(Include.NON_NULL) public Integer _size;
	@JsonInclude(Include.NON_NULL) public Integer _index;


	@JsonIgnore
	public GridSet getGridList() throws Exception{
		GridSet ds = new GridSet();
		if(0<_result.size()) {
			Field[] fs =  _result.get(0).getClass().getFields();
			for(T t: _result) {
				for(Field f:fs) {
					String fName = f.getName();
					ds.set(fName, f.get(t));
				}
			}
		}
		
		return ds;
	}

}
