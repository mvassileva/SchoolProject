package edu.spsu.swe2313.group7.library.mapperTest;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static junit.framework.Assert.assertNotSame;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
	public void initializeUser() throws ParseException, Exception
	{
		User u = new User();
		u.setLastName("Vassileva");
		u.setFirstName("Maria");
		u.setDateOfBirth(format.parse("1989-09-01"));
		u.setAllowedCheckout(true);
		u.setBookCheckedOutCount(1);
		u.setBookCheckoutLimit(5);
		u.setLateFees(0);
		u.setLevel(UserLevel.LIBRARIAN);
		u.setPassword("TEST1");
		u.setUserName("mvassileva");
		
		Long id = pMap.addUser(u);
		
		AuthenticationToken token = aMapper.userLogin("mvassileva", "TEST1");
		assertNotNull("Null token returned, login faulty", token.getToken());
		
		AuthenticationToken token2 = aMapper.getAuthToken(token.getToken());
		
		assertEquals("Token's do not match expected", token, token2);

		
		
	}
}
