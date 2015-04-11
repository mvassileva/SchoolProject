package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
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

/**
 *
 * @author Robert Whitaker
 */
@Controller
@RequestMapping("/reports")
public class ReportController {
	private static final Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	public void setUserMapper(UserMapper mapper) {
		this.userMapper = mapper;
	}
	
	@Autowired
	@Qualifier("bookMapper")
	private BookMapper bookMapper;

	public void setBookMapper(BookMapper mapper) {
		this.bookMapper = mapper;
	}
	
	@Autowired
	@Qualifier("authorMapper")
	private AuthorMapper authorMapper;

	public void setAuthorMapper(AuthorMapper mapper) {
		this.authorMapper = mapper;
	}
	
	
	@PostConstruct
	private void init() {

	}
   

	@RequestMapping( value = "Fines",
			 method = RequestMethod.GET)
	public @ResponseBody List<User> getFinesList() {
		return userMapper.getUsersWithFines();
	}
	
	
	@RequestMapping( value = "BooksByAuthor/{authorId}",
			 method = RequestMethod.GET)
	public @ResponseBody List<Book> getBooksByAuthor(@PathVariable long authorId) {
		return bookMapper.getBooksByAuthor(authorId);
	}
	
	@RequestMapping( value = "overDueBooks",
			 method = RequestMethod.GET)
	public @ResponseBody List<Book> getOverDueBooks() {
		return bookMapper.getOverDueBooks();
	}
	
	/*
	@RequestMapping( value="",
			 method = RequestMethod.POST)
	@ResponseBody
	public User createUser(@RequestBody User p) {
		logger.debug("Found User:");
		logger.debug("Last Name: " + p.getLastName());
		logger.debug("First Name: " + p.getFirstName());

		Long id = mapper.addUser(p);
		logger.info("Saved User with id " + id);
		return p;	
	}
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.PUT,
			 produces = "application/json")
	@ResponseBody
	public void updateUser(@PathVariable long userId, @RequestBody User p) {
		mapper.updateUser(p);
	}
	
	
	
	@RequestMapping( value="{userId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody User findBookById(@PathVariable long userId) {
		logger.debug("Called Find User By Id");
		return mapper.getUserById(userId);
	}
*/
}
