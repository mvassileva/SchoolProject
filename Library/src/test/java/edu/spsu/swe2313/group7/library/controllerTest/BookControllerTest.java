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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
			Logger.getLogger(BookControllerTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		lib1.setAllowedCheckout(true);
		lib1.setBookCheckedOutCount(1);
		lib1.setBookCheckoutLimit(5);
		lib1.setLateFees(0);
		lib1.setUserLevel(UserLevel.LIBRARIAN);
		lib1.setUserName("lib1");
		lib1.setPassword("TEST1");
		
		userMapper.addUser(lib1);
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
}
