package ibs.test.dto.chirp;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonRootName;

import mecury.io.LocalProperties;

@JsonRootName(value = "gateway")
public class GateWayPack {
	
	public HashMap<String, Object> location = new HashMap<String, Object>();

	public boolean discoveryEnabled = true;
	
	public String description;
	
	public String id;
	
	public String name;

	public String gatewayProfileID = LocalProperties.get("lora.network.server.gatepro.id");
	
	public String networkServerID = LocalProperties.get("lora.network.server.netsvc.id");
	
	public String organizationID = LocalProperties.get("lora.network.server.orga.id");
	
	public String serviceProfileID = LocalProperties.get("lora.network.server.svcpro.id");
	
}
