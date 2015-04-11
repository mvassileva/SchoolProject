package edu.spsu.swe2313.group7.library.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class UserControllerTest
{

	private static SimpleDateFormat format;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper pMap;

	public UserControllerTest() {
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
	public void initializeUser() throws ParseException
	{
		User u = new User();
		u.setLastName("Vassileva");
		u.setFirstName("Maria");
		u.setDateOfBirth(format.parse("1989-09-01"));
		u.setAllowedCheckout(true);
		u.setBookCheckedOutCount(1);
		u.setBookCheckoutLimit(5);
		u.setLateFees(0);
	
		Long id = pMap.addUser(u);

		assertEquals("First Name doesn't match Expected!", "Maria", pMap.getUserById(id).getFirstName());
		assertEquals("Last Name doesn't match Expected!", "Vassileva", pMap.getUserById(id).getLastName());
		//assertEquals("Date of Birth doesn't match!", "Maria", pMap.getUserById(id).getDateOfBirth());
		assertEquals("Book Checked Out Count doesn't match!", 1, pMap.getUserById(id).getBookCheckedOutCount());
		assertEquals("Book Checkout Limit doesn't match!", 5, pMap.getUserById(id).getBookCheckoutLimit());
		assertEquals("Late Fees doesn't match!", 0, pMap.getUserById(id).getLateFees());
		assertEquals("Date of Birth doesn't match!", format.parse("1989-09-01"), pMap.getUserById(id).getDateOfBirth());
	}
}

