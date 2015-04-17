package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
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
	private static final Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("authMapper")
	private AuthenticationMapper authMapper;
	
	public void setUserMapper(UserMapper mapper) {
		this.userMapper = mapper;
	}
	
	@PostConstruct
	private void init() {
		User p = new User();
		p.setFirstName("Bob");
		p.setLastName("Jones");
		p.setDateOfBirth(new Date());
		p.setLateFees(2);
		p.setBookCheckoutLimit(12);
		p.setAllowedCheckout(true);
		p.setUserLevel(UserLevel.ADMINISTRATOR);
		p.setUserName("test12");
		p.setPassword("test12");
		createUser(p);
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
		return u;	
	}
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.PUT,
			 produces = "application/json")
	@ResponseBody
	public void updateUser(@PathVariable long userId, @RequestBody User u) {
		userMapper.updateUser(u);
	}
	
	
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody User findBookById(@PathVariable long userId) {
		logger.debug("Called Find User By Id");
		return userMapper.getUserById(userId);
	}
	
	@RequestMapping (value="",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody List<User> getUsers(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.LIBRARIAN)) {
		
			return userMapper.getUsers();
		} else {
			return (new ArrayList<User>());
		}
		
	}
}
