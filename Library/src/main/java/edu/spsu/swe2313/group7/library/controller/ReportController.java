package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.PatronMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.Patron;
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
	@Qualifier("patronMapper")
	private PatronMapper patronMapper;

	public void setPatronMapper(PatronMapper mapper) {
		this.patronMapper = mapper;
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
	public @ResponseBody List<Patron> getFinesList() {
		return patronMapper.getPatronsWithFines();
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
	public Patron createPatron(@RequestBody Patron p) {
		logger.debug("Found Patron:");
		logger.debug("Last Name: " + p.getLastName());
		logger.debug("First Name: " + p.getFirstName());

		Long id = mapper.addPatron(p);
		logger.info("Saved Patron with id " + id);
		return p;	
	}
	
	@RequestMapping( value="{patronId}",
			 method = RequestMethod.PUT,
			 produces = "application/json")
	@ResponseBody
	public void updatePatron(@PathVariable long patronId, @RequestBody Patron p) {
		mapper.updatePatron(p);
	}
	
	
	
	@RequestMapping( value="{patronId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody Patron findBookById(@PathVariable long patronId) {
		logger.debug("Called Find Patron By Id");
		return mapper.getPatronById(patronId);
	}
*/
}
