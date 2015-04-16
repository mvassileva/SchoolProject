package edu.spsu.swe2313.group7.library.controllerTest;

import edu.spsu.swe2313.group7.library.controller.AuthenticationController;
import edu.spsu.swe2313.group7.library.controller.BookController;
import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.NameAndPassword;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Robert Whitaker
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class BookControllerTest {
	private static final Logger logger = Logger.getLogger(BookControllerTest.class);
	private static SimpleDateFormat format;
	
	//Controller under test
	@Autowired
	private BookController bookController;
	
	
	//Auth and User mapper, so we can control login and auth data to test
	//methods in the controller class
	@Autowired
	private AuthenticationMapper authMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	
	
	@BeforeClass
	public static void setUpClass()
	{
		format = new SimpleDateFormat("yyyy-MM-DD");
		//Setup dummy objects that we can preload with data
		/*
		AuthorMapper authorMap = new AuthorMapper();
		Author test1 = new Author();
		test1.setFirstName("Tester1First");
		test1.setLastName("Tester1Last");
		try {
			test1.setDateOfBirth(format.parse("1969-12-01"));
			test1.setDateOfDeath(format.parse("2001-01-31"));
		} catch (ParseException ex) {
			Logger.getLogger(BookControllerTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		test1.setBio("Call me Tester1First. Some years ago--never mind how long precisely --having little or no money in my purse and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world." );
		authorMap.addAuthor(test1);
		
		
		Author test2 = new Author();
		test2.setFirstName("Tester2First");
		test2.setLastName("Tester2Last");
		authorMap.addAuthor(test2);
		
		Author test3 = new Author();
		test3.setFirstName("Tester3First");
		test3.setLastName("Tester3Last");
		authorMap.addAuthor(test3);
		bookController.setAuthMapper(); */
		

		
		
		
		

	}

	@AfterClass
	public static void tearDownClass()
	{
	}

	@Before
	public void setUp() {
		//Called before every test
		
		//Preload with admin user
		//This is useful for testing the restricted functions that require a librarian user
		
		User lib1 = new User();
		lib1.setFirstName("One");
		try {
			lib1.setDateOfBirth(format.parse("1989-09-01"));
		} catch (ParseException ex) {
			logger.error("Unable to parse Date", ex);
		}
		lib1.setAllowedCheckout(true);
		lib1.setBookCheckoutLimit(5);
		lib1.setLateFees(0);
		lib1.setUserLevel(UserLevel.LIBRARIAN);
		lib1.setUserName("lib1");
		lib1.setPassword("TEST1");
		
		userMapper.addUser(lib1);
		
		// Setup patron id to use for testing
		User patron1 = new User();
		patron1.setLastName("Patron1");
		patron1.setFirstName("Patron1");
		patron1.setBookCheckoutLimit(8);
		patron1.setAllowedCheckout(true);
		patron1.setUserLevel(UserLevel.PATRON);
		patron1.setUserName("patron1");
		
		userMapper.addUser(patron1);
	}

	@After
	public void tearDown()
	{
	}
	
	@Ignore
	@Test
	public void bogusSpringTest()
	{
		assertNotNull("bookController is null, unable to complete further testsing", bookController);
	}

	
	
	@Test
	public void loginTest() throws Exception {
		AuthenticationToken token = authMapper.userLogin("lib1","TEST1");
		assertNotNull("Token recieved is null", token);
		//Now that we have a token, lets add a book(using the token)
		
		assertTrue(authMapper.verifyUserAccessLevel("lib1", token.getToken(), UserLevel.LIBRARIAN));
	}
	
	@Test
	public void createBookTest() throws Exception {
		AuthenticationToken token = authMapper.userLogin("lib1","TEST1");
		assertNotNull("Token recieved is null", token);
		//Now that we have a token, lets add a book(using the token)
		
		//Add a book
		Book b = new Book();
		Author a = new Author();
		a.setFirstName("John");	
		a.setLastName("Smith");
		b.setAuthor(a);
		b.setISBN13("37");
		b.setTitle("Test Book 1");
		
		Book bookResult = bookController.createBook("lib1", token.getToken(), b);
		assertEquals("Titles do not match", b.getTitle(), bookResult.getTitle());
	}
	
	
	@Test
	public void testCheckout() throws Exception {
		//Load up some data!
		createBookTest();
		//Setup auth
		AuthenticationToken token = authMapper.userLogin("lib1","TEST1");
		assertNotNull("Token recieved is null", token);
		
		//Find user id
		logger.debug("Attempting to load user by name");
		User p = userMapper.getUserByUserName("patron1");	
		logger.debug("Getting Books with the title 'Test Book 1'");
		List<Book> bList = bookController.getBookByTitle("Test Book 1");
		if (bList != null && !bList.isEmpty()) {
			for (Book b : bList ) {
				if (b == null) {
					logger.error("Book found to be null");
					continue;
				}
				logger.debug("Found Book, with Title: " + b.getTitle() + " and id: " + b.getId());
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, b.getCheckOutDuration());
				logger.debug("Calling checkout book, with book id =" + b.getId() + " and user id: " + p.getId());
				Date d = bookController.checkOutBook("lib1", token.getToken(), b.getId(), p.getId());
				assertNotNull("Checkout returned null!", d);
				
				
				//This is kind of goofy so bear with me.
				
				//Convert both dates to epoch seconds
				//Then use integer division to fix any time discrepancies
				long returnedDueDate = d.getTime();
				long expectedDueDate = cal.getTime().getTime();
				
				//This converts both to the nearest day(in days since epoch)
				long rDueDate = returnedDueDate/86400000;
				long eDueDate = expectedDueDate/86400000;
				//Now we just assert that the longs are equal, we could convert back to Dates and they should match
				//but it's extra work, and doesn't get you much but pretty printing at this point.
				assertEquals("Due date not matched", eDueDate, rDueDate);

			}
		} else {
			logger.error("No books found");
		}
		//TODO: fix
		

		logger.debug("Getting the book again, to see if the correct user has it checked out");
		List<Book> bList2 = bookController.getBookByTitle("Test Book 1");
		if (bList2 != null && !bList.isEmpty()) {
			for (Book b : bList2 ) {
				if (b == null) {
					logger.error("Book found to be null");
					continue;
				}
				logger.debug("Found Book, with Title: " + b.getTitle() + " and id: " + b.getId());
				assertEquals("Correct User not found", p.getUserName(),b.getCheckedOutBy().getUserName());
			}
		}
	}
	
	@Test
	public void testCheckoutFail() throws Exception {
		//Setup auth
		AuthenticationToken token = authMapper.userLogin("lib1","TEST1");
		assertNotNull("Token recieved is null", token);
		
		//Find user id
		User p = userMapper.getUserByUserName("patron1");	
		Date d = bookController.checkOutBook("lib1", token.getToken(), 12, p.getId());
		//This should fail
		assertTrue("Checkout Succeeded when it should have failed, check book id 12?", d == null);
	}
}
