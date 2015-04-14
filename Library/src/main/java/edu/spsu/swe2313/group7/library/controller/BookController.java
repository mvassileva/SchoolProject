package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
@RequestMapping("/book")
public class BookController {
	private static final Logger logger = Logger.getLogger(BookController.class);
	private static SimpleDateFormat format;
	
	@Autowired
	@Qualifier("bookMapper")
	private BookMapper bookMapper;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("authMapper")
	private AuthenticationMapper authMapper;

	public void setBookMapper(BookMapper mapper) {
		this.bookMapper = mapper;
	}

	public void setAuthMapper(AuthenticationMapper authMapper) {
		this.authMapper = authMapper;
	}
	
	@PostConstruct
	private void init() throws ParseException {
		format = new SimpleDateFormat("yyyy-MM-DD");
		//dummyDataLoad();
	}
   
	//For Weird Testing, remove soon?
	//
	private void dummyDataLoad() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		for (int i=0; i<300; i++) {
			Author a = new Author();
			a.setLastName("Dickens");
			a.setFirstName("Charles");
			Book b = new Book();
			b.setTitle("Book:"+i);
			b.setAuthor(a);
			b.setISBN13(i*20 + i*30 + i*i+1 + i*30 + i+17*20 + "-" + i*i +i*i*i + 30*i);
			if (i % 3 == 0 ) {
				b.setStatus(BookStatus.CHECKEDOUT);
				b.setDueDate(format.parse("2015-01-01"));
			} else {
				b.setStatus(BookStatus.CHECKEDIN);
			}
			bookMapper.addBook(b);
		}
	}
	
	/**
	 *
	 * @return
	 */
	@RequestMapping( value = "",
			 method = RequestMethod.GET)
	public @ResponseBody List<Book> getBookList() {
		return bookMapper.getBooks();
		//return booksList;
	}
	
	@RequestMapping( value="",
			 method = RequestMethod.POST)
	@ResponseBody
	public Book createBook(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @RequestBody Book b) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.LIBRARIAN)) {
			logger.debug("Found Book:");
			logger.debug("Title: " + b.getTitle());
			logger.debug("ISBN10: " + b.getISBN10());
			logger.debug("ISBN13: " + b.getISBN13());
			logger.debug("Authors: ");
			if (b.getAuthors() != null) {
				for (Author a : b.getAuthors()) {
					logger.debug(a.getFirstName());
				}
			} else {
				logger.debug("NONE!");
			}
						//b.setId(lastIndex()+1);
			//booksList.add(b);
			Long id = bookMapper.addBook(b);
			logger.info("Saved Book with id " + id);
			return b;
		}

		//In all other sceanarios this is not allowed!
		//todo: fix up return code
		return null;
	}
	
	@RequestMapping( value="{bookId}",
			 method = RequestMethod.PUT,
			 produces = "application/json")
	@ResponseBody
	public Book updateBook(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @PathVariable long bookId, @RequestBody Book b) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.LIBRARIAN)) {
			logger.debug("Found Book:");
			logger.debug("Title: " + b.getTitle());
			logger.debug("ISBN10: " + b.getISBN10());
			logger.debug("ISBN13: " + b.getISBN13());
			logger.debug("Authors: ");
			if (b.getAuthors() != null) {
				for (Author a : b.getAuthors()) {
					logger.debug(a.getFirstName());
				}
			} else {
				logger.debug("NONE!");
			}
			bookMapper.updateBook(b);
			return b;
		}

		//In all other sceanarios this is not allowed!
		//todo: fix up return code
		return null;

	}
	
	
	
	@RequestMapping( value="{bookId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody Book findBookById(@PathVariable long bookId) {
		logger.debug("Called Find Book By Id");
		return bookMapper.getBookById(bookId);
	}
	
	@RequestMapping( value="/checkout/{bookId}/{userId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	@ResponseBody
	public Date checkOutBook(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @PathVariable long bookId, @PathVariable long userId) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.JRLIBRARIAN)) {
			Book b = bookMapper.getBookById(bookId);
			User u = userMapper.getUserById(userId);

			if (b != null && u != null) {
				// Book and user are valid
				if (b.getStatus() == BookStatus.CHECKEDIN
					&& u.checkCheckoutStatus()) {
								//Book is checked in
					//User is allowed to check out
					b.setStatus(BookStatus.CHECKEDOUT);
					b.setCheckedOutBy(u);
					u.setBookCheckedOutCount(u.getBookCheckedOutCount()+1);

					//Make a new date for right now, add the number of 
					//days for the book's checkout duration to it, and then set it.
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, b.getCheckOutDuration());
					b.setDueDate(cal.getTime());
					return b.getDueDate();
				}

			}
		}
		//Under other circumstances error
		return null;
	}


	@RequestMapping( value="/checkin/{bookId}/",
			 method = RequestMethod.GET,
			 produces = "application/json")
	@ResponseBody
	public Integer checkInBook(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @PathVariable long bookId) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.JRLIBRARIAN)) {
			Book b = bookMapper.getBookById(bookId);
			if (b != null) {
				User u = b.getCheckedOutBy();
				if (u!= null) {
					// Book and user are valid
					if (b.getStatus() == BookStatus.CHECKEDOUT) {
						b.setStatus(BookStatus.CHECKEDIN);
						b.setCheckedOutBy(null);
						u.setBookCheckedOutCount(u.getBookCheckedOutCount()-1);
						return u.getLateFees();
					}
				}
			}
		}
		//Under other circumstances error
		return null;
	}


	

}
