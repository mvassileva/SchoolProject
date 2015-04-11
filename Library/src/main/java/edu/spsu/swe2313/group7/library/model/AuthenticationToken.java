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
	private Date experation;
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getExperation() {
		return experation;
	}

	public void setExperation(Date experation) {
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
