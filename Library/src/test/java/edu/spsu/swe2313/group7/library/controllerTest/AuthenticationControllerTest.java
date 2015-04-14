package edu.spsu.swe2313.group7.library.controllerTest;

import com.google.gson.Gson;
import edu.spsu.swe2313.group7.library.controller.AuthenticationController;
import edu.spsu.swe2313.group7.library.controller.BookController;
import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.NameAndPassword;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author Robert Whitaker
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class AuthenticationControllerTest {
	private static SimpleDateFormat format;
	
	
	@Autowired
	private AuthenticationController authController;
	
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
		//preload();

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void mocktest() throws Exception {
		NameAndPassword unp = new NameAndPassword();
		
		Gson gson = new Gson();
		String json = gson.toJson(unp, NameAndPassword.class);

	}
	
	@Ignore
	@Test
	public void bogusSpringTest()
	{
		assertNotNull("authController is null, unable to complete further testsing", authController);
	}

	
	@Ignore
	@Test
	public void loginTest() {
		NameAndPassword testLogin = new NameAndPassword();
		testLogin.setUserName("lib1");
		testLogin.setUserName("TEST1");
		String token = authController.login(testLogin);
		assertNotNull("Token recieved is null", token);
		
		
		//Add a book
		Book b = new Book();
		Author a = new Author();
		a.setFirstName("John");	
		a.setLastName("Smith");
		b.setAuthor(a);
		b.setISBN13("37");
		b.setTitle("Test Book 1");
		
		//Book bookResult = bookController.createBook(b);
		//assertEquals("Titles do not match", b.getTitle(), bookResult.getTitle());
		//mockMvc.perform(get("/auth/login"));
		
	}
	
private String preload() throws Exception {
		User u = new User();
		u.setLastName("Libraian");
		u.setFirstName("One");
		u.setDateOfBirth(format.parse("1989-09-01"));
		u.setAllowedCheckout(true);
		u.setBookCheckedOutCount(1);
		u.setBookCheckoutLimit(5);
		u.setLateFees(0);
		u.setLevel(UserLevel.LIBRARIAN);
		u.setPassword("TEST1");
		u.setUserName("lib1");
		//userMapper.addUser(u);

		return null;
}

}
