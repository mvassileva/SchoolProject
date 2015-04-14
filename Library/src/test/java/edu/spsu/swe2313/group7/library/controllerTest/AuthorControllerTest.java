package edu.spsu.swe2313.group7.library.controllerTest;

import edu.spsu.swe2313.group7.library.controller.AuthorController;
import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
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
import org.junit.Before;
import org.junit.BeforeClass;
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
public class AuthorControllerTest {
	private static SimpleDateFormat format;
	
	@Autowired
	private AuthorController authorController;
	
	
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
	}

	@AfterClass
	public static void tearDownClass()
	{
	}

	@Before
	public void setUp() throws Exception {
		//Called before every test
		
		//Preload with admin user
		//This is useful for testing the restricted functions that require a librarian user
		
		User lib1 = new User();
		lib1.setFirstName("One");
		lib1.setLastName("Librarian");
		lib1.setUserLevel(UserLevel.LIBRARIAN);
		lib1.setUserName("lib1");
		lib1.setPassword("TEST1");
		
		userMapper.addUser(lib1);

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void bogusSpringTest()
	{
		assertNotNull("authprController is null, unable to complete further testsing", authorController);
	}
	
	@Test
	public void createAuthorTest() throws Exception {
		AuthenticationToken token = authMapper.userLogin("lib1","TEST1");
		assertNotNull("Token recieved is null", token);
		//Now that we have a token, lets add a book(using the token)
		
		//Add a book
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
		Author result = authorController.createAuthor("lib1", token.getToken(), test1);
		assertEquals("FirstNames do not match", test1.getFirstName(),result.getFirstName());
		
		//Check to see if the stored author matches the one we sent
		assertEquals("LastNames do not match", test1.getLastName(),result.getLastName());
		assertEquals("Dates of Birth do not match", test1.getDateOfBirth(),result.getDateOfBirth());
		assertEquals("Dates of Death do not match", test1.getDateOfDeath(),result.getDateOfDeath());
		assertEquals("Bios do not match", test1.getBio(),result.getBio());
		//Book bookResult = bookController.createBook("lib1", token.getToken(), b);
		
	}
}
