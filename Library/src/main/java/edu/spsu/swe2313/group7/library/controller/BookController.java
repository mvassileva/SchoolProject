package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import java.util.ArrayList;
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
@RequestMapping("/book")
public class BookController {
	private static final Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	@Qualifier("bookMapper")
	private BookMapper mapper;

	public void setMapper(BookMapper mapper) {
		this.mapper = mapper;
	}
	
	@PostConstruct
	private void init() {
		//dummyDataLoad();
	}
   
	//For Weird Testing, remove soon?
	//
	private void dummyDataLoad() {
		for (int i=0; i<300; i++) {
			Author a = new Author();
			a.setLastName("Dickens");
			a.setFirstName("Charles");
			Book b = new Book();
			List<Author> aList = new ArrayList(1);
			aList.add(a);
			//b.setAuthors(aList);
			b.setTitle("Book:"+i);
			//b.setId(i);
			b.setISBN13(i*20 + i*30 + i*i+1 + i*30 + i+17*20 + "-" + i*i +i*i*i + 30*i);
			b.setStatus(BookStatus.CHECKEDIN);
			//booksList.add(b);
			mapper.addBook(b);
		}
	}
	
	/**
	 *
	 * @return
	 */
	@RequestMapping( value = "",
			 method = RequestMethod.GET)
	public @ResponseBody List<Book> getBookList() {
		return mapper.getBooks();
		//return booksList;
	}
	
	@RequestMapping( value="",
			 method = RequestMethod.POST)
	@ResponseBody
	public Book createBook(@RequestBody Book b) {
		logger.debug("Found Book:");
		logger.debug("Title: " + b.getTitle());
		logger.debug("ISBN10: " + b.getISBN10());
		logger.debug("ISBN13: " + b.getISBN13());
		logger.debug("Authors: " );
		if (b.getAuthors() != null ) {
			for (Author a : b.getAuthors()) {
				logger.debug(a.getFirstName());
			}
		} else {
			logger.debug("NONE!");
		}
		//b.setId(lastIndex()+1);
		//booksList.add(b);
		Long id = mapper.addBook(b);
		logger.info("Saved Book with id " + id);
		return b;	
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
		mapper.updateBook(b);
		
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
		return mapper.getBookById(bookId);
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
