package ibs.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class DOWN extends IbsPack {
	
	@JsonIgnore public static final String AAA = "A";
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public boolean confirmed = true;
	
	@JsonProperty(access = Access.READ_WRITE)
	public String devEUI;
	
	@JsonProperty(access = Access.READ_WRITE)
	public int fPort;
	
	@JsonProperty(access = Access.READ_WRITE)
	public String data;
}
