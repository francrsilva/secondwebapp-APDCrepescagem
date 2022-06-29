package secondwebapp.util;

import java.util.UUID;

public class AuthToken {
	public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2h
	public String username;
	public String tokenID;
	public long creationData;
	public long expirationData;
	public long role;

	public AuthToken(String username, long role) {
		this.username = username;
		this.tokenID = UUID.randomUUID().toString();
		this.creationData = System.currentTimeMillis();
		this.expirationData = this.creationData + AuthToken.EXPIRATION_TIME;
		this.role=role;
		}
	
	public Boolean isValid() {
		return expirationData > System.currentTimeMillis();
	}
}