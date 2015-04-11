package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.NameAndPassword;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.util.PasswordHash;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Robert Whitaker
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	@Qualifier("authMapper")
	private AuthMapper authMapper;
	
	@RequestMapping( value="/login",
			 method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody NameAndPassword nameAndPassword) {
		if ( nameAndPassword != null) {
			String username = nameAndPassword.getUserName();
			String password = nameAndPassword.getPassWord();
			try {
				AuthenticationToken token = authMapper.userLogin(username, password);
				return token.getToken();
			} catch (Exception ex) {
				Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
				return "{ Error: login Failed }";
			}
			
		}
		return "{ Error: parse error }";
	}
	
	
	
}
