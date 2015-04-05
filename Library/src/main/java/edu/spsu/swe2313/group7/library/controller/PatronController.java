package edu.spsu.swe2313.group7.library.controller;

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
@RequestMapping("/patron")
public class PatronController {
	private static final Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	@Qualifier("patronMapper")
	private PatronMapper mapper;

	public void setMapper(PatronMapper mapper) {
		this.mapper = mapper;
	}
	
	@PostConstruct
	private void init() {
		Patron p = new Patron();
		p.setFirstName("Bob");
		p.setLastName("Jones");
		p.setDateOfBirth(new Date());
		p.setLateFees(2);
		p.setBookCheckoutLimit(12);
		p.setBookCheckedOutCount(0);
		p.setAllowedCheckout(true);
		createPatron(p);
	}
   
	/**
	 *
	 * @return
	 */
	@RequestMapping( value = "",
			 method = RequestMethod.GET)
	public @ResponseBody List<Patron> getPatronList() {
		return mapper.getPatrons();
		//return booksList;
	}
	
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
}
