package edu.spsu.swe2313.group7.library.model;

/**
 *
 * @author Robert Whitaker
 * 
 * For easy json submission of U/P for login, should most likely occur over ssl
 *
 */
public class NameAndPassword {
	private String userName;
	private String passWord;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
