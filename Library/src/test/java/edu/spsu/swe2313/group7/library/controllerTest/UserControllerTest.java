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

import edu.spsu.swe2313.group7.library.dao.PatronMapper;
import edu.spsu.swe2313.group7.library.model.Patron;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class PatronControllerTest
{

	private static SimpleDateFormat format;

	@Autowired
	@Qualifier("patronMapper")
	private PatronMapper pMap;

	public PatronControllerTest() {
	}

	@BeforeClass
	public static void setUpClass()
	{
		format = new SimpleDateFormat("YYYY-MM-DD");

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
		assertNotNull("Patron Mapper DAO object is null, unable to complete further testsing", pMap);
	}

	@Test
	public void initializePatron() throws ParseException
	{
		Patron p = new Patron();
		p.setLastName("Vassileva");
		p.setFirstName("Maria");
		p.setDateOfBirth(format.parse("09/01/1989"));
		p.setAllowedCheckout(true);
		p.setBookCheckedOutCount(1);
		p.setBookCheckoutLimit(5);
		p.setLateFees(0);
	
		Long id = pMap.addPatron(p);

		assertEquals("First Name doesn't match Expected!", "Maria", pMap.getPatronById(id).getFirstName());
		assertEquals("Last Name doesn't match Expected!", "Vassileva", pMap.getPatronById(id).getLastName());
		assertEquals("Date of Birth doesn't match!", "Maria", pMap.getPatronById(id).getDateOfBirth());
		assertEquals("Book Checked Out Count doesn't match!", 1, pMap.getPatronById(id).getBookCheckedOutCount());
		assertEquals("Book Checkout Limit doesn't match!", 5, pMap.getPatronById(id).getBookCheckoutLimit());
		assertEquals("Late Fees doesn't match!", 0, pMap.getPatronById(id).getLateFees());
		assertEquals("Date of Birth doesn't match!", format.parse("09/01/1989"), pMap.getPatronById(id).getDateOfBirth());
	}
}

