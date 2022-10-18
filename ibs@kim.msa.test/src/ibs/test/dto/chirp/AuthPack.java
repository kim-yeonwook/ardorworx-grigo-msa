package ibs.test.dto.chirp;

import mecury.io.LocalProperties;

public class AuthPack {

	public String email = LocalProperties.get("lora.network.server.id", "admin");
	
	public String password = LocalProperties.get("lora.network.server.pass", "admin");
}
	