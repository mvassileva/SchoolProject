package edu.spsu.swe2313.group7.library.model;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Robert Whitaker
 *
 *
 * Just a temporary token for use in the app.
 */
public class AuthenticationToken {

	private String userName;
	private long experation;
	private String token;
	private UserLevel level;
	private boolean error;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	public UserLevel getLevel() {
		return level;
	}

	public void setLevel(UserLevel level) {
		this.level = level;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getExperation() {
		return experation;
	}

	public void setExperation(long experation) {
		this.experation = experation;
	}

	public String generateToken() {
		token = UUID.randomUUID().toString();
		return token;
	}

	public String getToken() {
		return token;
	}
	
	

}
