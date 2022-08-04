package ibs.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties({"propertiTest1", "propertiTest2"})
public class JackSonTestPack extends IbsPack {
	
	@JsonIgnore
	public String ignoreString;
	
	@JsonInclude(Include.ALWAYS)
	public String jsonIncludeAlways;
	
	@JsonInclude(Include.NON_ABSENT)
	public String jsonIncludeNAbsent;
	
	@JsonInclude(Include.NON_DEFAULT)
	public int jsonIncludeNDefault;

	@JsonInclude(Include.NON_EMPTY)
	public String jsonIncludeNEmpty;
	
	@JsonInclude(Include.CUSTOM)
	public String jsonIncludeCustom;
	
	public String propertiTest1;
	
	public String propertiTest2;
	
}
