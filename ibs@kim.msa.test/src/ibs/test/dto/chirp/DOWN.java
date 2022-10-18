package ibs.test.dto.chirp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ibs.test.dto.IbsPack;

public class DOWN extends IbsPack {
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public boolean confirmed = true;
	
	@JsonProperty(access = Access.READ_WRITE)
	public String devEUI;
	
	@JsonProperty(access = Access.READ_WRITE)
	public int fPort;
	
	@JsonProperty(access = Access.READ_WRITE)
	public String data;
}
