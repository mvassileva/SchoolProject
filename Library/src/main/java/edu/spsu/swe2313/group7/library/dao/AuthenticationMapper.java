package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import edu.spsu.swe2313.group7.library.util.PasswordHash;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Robert Whitaker
 */
@Transactional
@Service("authMapper")
public class AuthenticationMapper {
	private static final Logger logger = Logger.getLogger(AuthenticationMapper.class);
	private Map<String, AuthenticationToken> authMap;

	//TODO: properties file
	//One hour in ms
	private static Long sessionDuration = (long) 3600000;
	private PasswordHash hashFunc;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@PostConstruct
	public void init() throws ParseException {
		authMap = new HashMap<>();
		hashFunc = new PasswordHash();
				
		User test1 = new User();
		test1.setUserName("test1");
		test1.setPassword("test");
		userMapper.addUser(test1);
	}

	public void setUserMapper(UserMapper mapper) {
		this.userMapper = mapper;
	}


	public AuthenticationToken userLogin(String username, String password) throws Exception {
		if (username != null && password != null) {
			//Fetch user info and auth
			User u = userMapper.getUserByUserName(username);
			if (u != null) {
				if (u.getPasswordHash().equals(hashFunc.passwordToHash(password))) {
					// Everything looks legit, lets make a token.
					AuthenticationToken tok = new AuthenticationToken();
					tok.setUserName(username);
					String tokenValue = tok.generateToken();
					tok.setLevel(u.getUserLevel());
					tok.setExperation(System.currentTimeMillis() + sessionDuration);
					authMap.put(tokenValue, tok);
					return tok;
				}
			}
		}
		//Something was incorrect
		//TODO make friendly
		throw new Exception();
	}
	
	public AuthenticationToken getAuthToken(String token) {
		return authMap.get(token);
	}

	public void expireToken (AuthenticationToken token) {
		expireToken(token.getToken());
	}
	
	public void expireToken(String token) {
		authMap.remove(token);
	}
	
	/*
	This allows various functions to check to see if an action is allowed by sending
	the info they recieved from the end user and what minimum level they have requested
	
	Returns True if User and Token are valid and the user level meets or exceeds the minAccessLevel
	Returns false in all other cases
	*/
	public boolean verifyUserAccessLevel(String User, String token, UserLevel minAccessLevel) {
		UserLevel level = checkLogin(User, token);
		if (level == null) {
			logger.debug("CheckLogin returned with null");
			//Something went wrong.
			return false;
		}
		logger.debug("Checking User Priviledge Level");
		//This essentially ranks the user levels, so that if say an administrator was trying 
		//to access a feature that a JRLibrarian could, it would drop down to there and return
		//However in the opposite case, the functions would not return, and fall out of the switch
		//finally reaching the bottom return false statement
		switch (level) {
			case ADMINISTRATOR:
				if (minAccessLevel == UserLevel.ADMINISTRATOR) {
					logger.debug("Requested Level Administrator, current level " + level);
					return true;
				}
			case LIBRARIAN:
				if (minAccessLevel == UserLevel.LIBRARIAN) {
					logger.debug("Requested Level Librarian, current level " + level);
					return true;
				}
			case JRLIBRARIAN:
				if (minAccessLevel == UserLevel.JRLIBRARIAN) {
					logger.debug("Requested Level JRLibrarian, current level " + level);
					return true;
				}
			case OTHERSTAFF:
				if (minAccessLevel == UserLevel.OTHERSTAFF) {
					logger.debug("Requested Level OTHERStaff, current level " + level);
					return true;
				}
			case PATRON:
				if (minAccessLevel == UserLevel.PATRON) {
					logger.debug("Requested Level PATRON, current level " + level);
					return true;
				}
			case NOACCESS:
				logger.debug("Found no Access");
		}
		
		return false;
	}

	private UserLevel checkLogin(String user, String token) {
		AuthenticationToken testToken = this.getAuthToken(token);
		if (testToken == null) {
			//Token invalid
			logger.error("Invalid token recieved, User: " + user + ", token = " + token);
			return null;
		}
		//Check UserNames
		if (!user.equalsIgnoreCase(testToken.getUserName())) {
			//User name on token doesn't match token key
			logger.error("Invalid user name specified, User: " + user + ", doesn't match token user:" + testToken.getUserName());
			return null;
		}
		//Check Expiration
		if (testToken.getExperation() < System.currentTimeMillis()) {
			//Token Expired
			logger.error("Token Expired, for User: " + user + ", token = " + token);
			logger.error("Deleting token");
			expireToken(testToken);
			return null;
		}
		//Everything looks good, return the user level
		logger.debug("Returning user level");
		return testToken.getLevel();
	}

}
