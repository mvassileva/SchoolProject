package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
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
	
	@Autowired
	@Qualifier("bookMapper")
	private BookMapper bookMapper;
	
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
		if (key != null) {
			AuthenticationToken token = authMapper.getAuthToken(key);
			if (token != null) {
				if (userName.equalsIgnoreCase(token.getUserName())) {
					if (token.getLevel() == UserLevel.ADMINISTRATOR
						|| token.getLevel() == UserLevel.LIBRARIAN) {
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
				}
			}

		}
		
		//In all other sceanarios this is not allowed!
		//todo: fix up return code
		return null;
	}
	
	@RequestMapping( value="{bookId}",
			 method = RequestMethod.PUT,
			 produces = "application/json")
	@ResponseBody
	public void updateBook(@PathVariable long bookId, @RequestBody Book b) {
		/*
		for (Book oldbook : booksList) {
			if (oldbook.getId() == bookId) {
				logger.debug("Found Book!");
				oldbook.copyFromBook(b);
				logger.debug("Found Book:");
				logger.debug("Title: " + oldbook.getTitle());
				logger.debug("ISBN10: " + oldbook.getISBN10());
				logger.debug("ISBN13: " + oldbook.getISBN13());
				return b.getTitle();	
			} 
		}*/
		bookMapper.updateBook(b);
		
		//return "{\"Error\": \"Book not found, update impossoble\"";
	}
	
	
	
	@RequestMapping( value="{bookId}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody Book findBookById(@PathVariable long bookId) {
		logger.debug("Called Find Book By Id");
		/*for (Book b : booksList) {
			if (b.getId() == bookId) {
				logger.debug("Found Book!");
				return b;
			}
		}
		logger.debug("Didn't find book!");
		return null; */
		return bookMapper.getBookById(bookId);
	}
	/*
	private long lastIndex() {
		//this is bad, should be fixed
		long highest=0;
		for (Book b : booksList) {
			if (b.getId() > highest) {
				highest = b.getId();
			}
		}
		return highest;
	}
	*/
}
