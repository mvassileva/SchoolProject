package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author Robert Whitaker
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	private static SimpleDateFormat format;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("authMapper")
	private AuthenticationMapper authMapper;
	
	public void setUserMapper(UserMapper mapper) {
		this.userMapper = mapper;
	}
	@Value("${preload.test.data}")
	boolean preloadData;
	
	@PostConstruct
	public void init() throws ParseException {
		if (preloadData) {
			preload();
		}
	}
	

	private void preload() throws ParseException {
		format = new SimpleDateFormat("yyyy-MM-DD");
		
		User a = new User();
		a.setFirstName("Bob");
		a.setLastName("Jones");
		a.setDateOfBirth(format.parse("1963-12-11"));
		a.setLateFees(0);
		a.setBookCheckoutLimit(12);
		a.setAllowedCheckout(true);
		a.setUserLevel(UserLevel.ADMINISTRATOR);
		a.setUserName("test12");
		a.setPassword("test12");
		a.setEmailAddress("test12@admin.com");
		createUser(a);
		
		User p = new User();
		p.setFirstName("Stephen");
		p.setLastName("Jones");
		p.setDateOfBirth(format.parse("1983-01-29"));
		p.setLateFees(2);
		p.setBookCheckoutLimit(4);
		p.setAllowedCheckout(true);
		p.setUserLevel(UserLevel.PATRON);
		p.setUserName("sJones");
		p.setPassword("test");
		p.setEmailAddress("test12@patron.com");
		createUser(p);
		
		User l = new User();
		l.setFirstName("Stephen");
		l.setLastName("Brown");
		l.setDateOfBirth(format.parse("1981-01-29"));
		l.setLateFees(0);
		l.setBookCheckoutLimit(8);
		l.setAllowedCheckout(true);
		l.setUserLevel(UserLevel.LIBRARIAN);
		l.setUserName("sBrown");
		l.setPassword("test");
		l.setEmailAddress("test12@librarian.com");
		createUser(l);
	}
   
	/**
	 *
	 * @return
	 */
	@RequestMapping( value = "",
			 method = RequestMethod.GET)
	public @ResponseBody List<User> getUserList() {
		return userMapper.getUsers();
		//return booksList;
	}
	
	@RequestMapping( value="",
			 method = RequestMethod.POST)
	@ResponseBody
	public User createUser(@RequestBody User u) {
		logger.debug("Found User:");
		logger.debug("Last Name: " + u.getLastName());
		logger.debug("First Name: " + u.getFirstName());

		Long id = userMapper.addUser(u);
		logger.info("Saved User with id " + id);
		User responseUser = userMapper.getUserById(id);
		return responseUser;	
	}
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.PUT,
			 produces = "application/json")
	@ResponseBody
	public void updateUser(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @PathVariable long userId, @RequestBody User u) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.ADMINISTRATOR)) {
			userMapper.updateUser(u);
		}
	}
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.DELETE,
			 produces = "application/json")
	@ResponseBody
	public boolean removeUser(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @PathVariable long userId) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.LIBRARIAN)) {
			userMapper.removeUser(userId);
			return true;
		}

		//In all other sceanarios this is not allowed!
		//todo: fix up return code
		return false;

	}
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody User findBookById(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @PathVariable long userId) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.ADMINISTRATOR)) {
			logger.debug("Called Find User By Id");
			return userMapper.getUserById(userId);
		} else {
			return (new User());
		}
	}
	
	@RequestMapping (value="",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody List<User> getUsers(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.ADMINISTRATOR)) {
		
			return userMapper.getUsers();
		} else {
			return (new ArrayList<User>());
		}
		
	}
}
