package edu.spsu.swe2313.group7.library.MapperTest;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Robert Whitaker
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class AuthenticationMapperTest {
	private static SimpleDateFormat format;

	@Autowired
	@Qualifier("authMapper")
	private AuthenticationMapper aMapper;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper pMap;
	
	
	
	public AuthenticationMapperTest() {
	}

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
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void bogusSpringTest()
	{
		assertNotNull("User Mapper DAO object is null, unable to complete further testsing", pMap);
	}

	@Test
	public void loginUserTest() throws ParseException, Exception
	{
		User u = new User();
		u.setLastName("Vassileva");
		u.setFirstName("Maria");
		u.setDateOfBirth(format.parse("1989-09-01"));
		u.setAllowedCheckout(true);
		u.setBookCheckoutLimit(5);
		u.setLateFees(0);
		u.setUserLevel(UserLevel.LIBRARIAN);
		u.setPassword("TEST1");
		u.setUserName("mvassileva");
		
		Long id = pMap.addUser(u);
		
		AuthenticationToken token = aMapper.userLogin("mvassileva", "TEST1");
		assertNotNull("Null token returned, login faulty", token.getToken());
		
		AuthenticationToken token2 = aMapper.getAuthToken(token.getToken());
		
		assertEquals("Token's do not match expected", token, token2);

		//Test User levels
		//Since our user is a Libraian, this should return true for all except administrator test
		assertFalse(aMapper.verifyUserAccessLevel("mvassileva", token.getToken(), UserLevel.ADMINISTRATOR));
		assertTrue(aMapper.verifyUserAccessLevel("mvassileva", token.getToken(), UserLevel.LIBRARIAN));
		assertTrue(aMapper.verifyUserAccessLevel("mvassileva", token.getToken(), UserLevel.JRLIBRARIAN));
		assertTrue(aMapper.verifyUserAccessLevel("mvassileva", token.getToken(), UserLevel.OTHERSTAFF));
		assertTrue(aMapper.verifyUserAccessLevel("mvassileva", token.getToken(), UserLevel.PATRON));
		
		// Check failed login
		assertFalse(aMapper.verifyUserAccessLevel("mvassileva", token.getToken()+11111, UserLevel.LIBRARIAN));
		
		User u1 = new User();
		u1.setUserName("tester1");
		u1.setUserLevel(UserLevel.NOACCESS);
		u1.setPassword("test1");
		pMap.addUser(u1);
		
		AuthenticationToken token3 = aMapper.userLogin("tester1", "test1");
		assertFalse(aMapper.verifyUserAccessLevel("tester1", token3.getToken(), UserLevel.ADMINISTRATOR));
		assertFalse(aMapper.verifyUserAccessLevel("tester1", token3.getToken(), UserLevel.PATRON));
	}
	
	@Test
	public void expireTokenTest() throws ParseException, Exception {
		User u1 = new User();
		u1.setUserName("tester2");
		u1.setUserLevel(UserLevel.ADMINISTRATOR);
		u1.setPassword("test2");
		pMap.addUser(u1);
		AuthenticationToken token = aMapper.userLogin("tester2", "test2");
		//Verify that we have a good session, this should return true saying we are and admin
		assertTrue(aMapper.verifyUserAccessLevel("tester2", token.getToken(), UserLevel.ADMINISTRATOR));
		//Expire token
		aMapper.expireToken(token);
		//Token should now be invalid:
		assertFalse(aMapper.verifyUserAccessLevel("tester2", token.getToken(), UserLevel.ADMINISTRATOR));
		
		
	}
	
}
