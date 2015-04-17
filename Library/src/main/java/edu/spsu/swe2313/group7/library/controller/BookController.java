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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
		preload();
	}
   
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
//TODO: move to book mapper?
			if (b != null && u != null) {
				logger.debug("Attempting checkout book id:" + b.getId() + " to User Id: " + u.getId());
				// Book and user are valid
				if (b.getStatus() == BookStatus.CHECKEDIN
					&& u.checkCheckoutStatus()) {
					if ( bookMapper.checkOut(b, u) ) {
						//Since checkout was successful, the object has been updated,
						//however since we are working with detached objects, we must update
						//it explicitly to find out the due date.
						b = bookMapper.getBookById(bookId);
						return b.getDueDate();
					} else {
						logger.error("Checkout Failed");
					}
					
				} else {
					logger.debug("Book Status is " + b.getStatus());
					logger.debug("User Checkout Status is " + u.checkCheckoutStatus());
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
						return u.getLateFees();
					}
				}
			}
		}
		//Under other circumstances error
		return null;
	}
	
	@RequestMapping( value = "/title/{title}",
			 method = RequestMethod.GET,
			 produces = "application/json")
	public @ResponseBody List<Book> getBookByTitle(@PathVariable String title) {
		return bookMapper.getBookByTitle(title);
	}

	
	private void preload() throws ParseException {
		Author auth1 = new Author();
		auth1.setFirstName("Jane");
		auth1.setLastName("Austen");
		Book book1 = new Book();
		book1.setAuthor(auth1);
		book1.setTitle("Pride and Prejudice");
		book1.setPublishDate(format.parse("1813-01-01"));
		book1.setShelf("A-123");
		bookMapper.addBook(book1);
		
		Author auth2 = new Author();
		auth2.setFirstName("Harper");
		auth2.setLastName("Lee");
		Book book2 = new Book();
		book2.setAuthor(auth2);
		book2.setTitle("To Kill a Mockingbird");
		book2.setPublishDate(format.parse("1960-01-01"));
		book2.setShelf("A-123");
		bookMapper.addBook(book2);
		
		Author auth3 = new Author();
		auth3.setFirstName("F. Scott");
		auth3.setLastName("Fitzgerald");
		Book book3 = new Book();
		book3.setAuthor(auth3);
		book3.setTitle("The Great Gatsby");
		book3.setPublishDate(format.parse("1925-01-01"));
		book3.setShelf("A-124");
		bookMapper.addBook(book3);
		
		Author auth4 = new Author();
		auth4.setFirstName("Charlotte");
		auth4.setLastName("Bronte");
		Book book4 = new Book();
		book4.setAuthor(auth4);
		book4.setTitle("Jane Eyre");
		book4.setPublishDate(format.parse("1847-01-02"));
		book4.setShelf("A-125");
		bookMapper.addBook(book4);
		
		Author auth5 = new Author();
		auth5.setFirstName("George");
		auth5.setLastName("Orwell");
		Book book5 = new Book();
		book5.setAuthor(auth5);
		book5.setTitle("1984");
		book5.setPublishDate(format.parse("1948-01-01"));
		book5.setShelf("B-133");
		bookMapper.addBook(book5);
		
		Author auth6 = new Author();
		auth6.setFirstName("J.D.");
		auth6.setLastName("Salinger");
		Book book6 = new Book();
		book6.setAuthor(auth6);
		book6.setTitle("The Catcher in the Rye");
		book6.setPublishDate(format.parse("1951-01-01"));
		book6.setShelf("C-13");
		bookMapper.addBook(book6);
		
		Book book7 = new Book();
		book7.setAuthor(auth5);
		book7.setTitle("Animal Farm");
		book7.setPublishDate(format.parse("1945-01-01"));
		book7.setShelf("D-234");
		bookMapper.addBook(book7);
		
		Author auth8 = new Author();
		auth8.setFirstName("Emily");
		auth8.setLastName("Bronte");
		Book book8 = new Book();
		book8.setAuthor(auth8);
		book8.setTitle("Wuthering Heights");
		book8.setPublishDate(format.parse("1847-01-01"));
		book8.setShelf("E-223");
		bookMapper.addBook(book8);
		
		Author auth9 = new Author();
		auth9.setFirstName("Louisa May");
		auth9.setLastName("Alcott");
		Book book9 = new Book();
		book9.setAuthor(auth9);
		book9.setTitle("Little Women");
		book9.setPublishDate(format.parse("1868-01-01"));
		book9.setShelf("F-312");
		bookMapper.addBook(book9);
		
		Author auth10 = new Author();
		auth10.setFirstName("William");
		auth10.setLastName("Golding");
		Book book10 = new Book();
		book10.setAuthor(auth10);
		book10.setTitle("Lord of the Flies");
		book10.setPublishDate(format.parse("1954-01-01"));
		book10.setShelf("G-349");
		bookMapper.addBook(book10);
		
	}
	

}
