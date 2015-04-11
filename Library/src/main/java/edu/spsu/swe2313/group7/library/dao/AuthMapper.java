package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.util.PasswordHash;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Robert Whitaker
 */
@Transactional
@Service("authMapper")
public class AuthMapper {

	private Map<String, AuthenticationToken> authMap;

	private PasswordHash hashFunc;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper mapper;

	@PostConstruct
	private void init() throws ParseException {
		authMap = new HashMap<>();
		hashFunc = new PasswordHash();
	}

	public AuthenticationToken userLogin(String username, String password) throws Exception {
		if (username != null && password != null) {
			//Fetch user info and auth
			User u = mapper.getUserByName(username);
			if (u != null) {
				if (u.getPasswordHash().equals(hashFunc.passwordToHash(password))) {
					// Everything looks legit, lets make a token.
					AuthenticationToken tok = new AuthenticationToken();
					tok.setUserName(username);
					String tokenValue = tok.generateToken();
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

}
