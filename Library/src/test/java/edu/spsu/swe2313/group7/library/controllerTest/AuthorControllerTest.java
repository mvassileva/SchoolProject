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

import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.model.Author;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class AuthorControllerTest
{

	private static SimpleDateFormat format;

	@Autowired
	@Qualifier("authorMapper")
	private AuthorMapper aMap;

	public AuthorControllerTest() {
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
		assertNotNull("Author Mapper DAO object is null, unable to complete further testsing", aMap);
	}

	@Test
	public void addAuthorTest() throws ParseException
	{
		// Book test, our text book :)
		Author a = new Author();
		a.setLastName("Tsui");
		a.setFirstName("Frank");
		a.setDateOfBirth(format.parse("01/01/1960")); //just a bogus date for testing
		
		Long id = aMap.addAuthor(a);

		assertEquals("First Name doesn't match", "Frank", aMap.getAuthorById(id).getFirstName());
		assertEquals("Last Name doesn't match", "Tsui", aMap.getAuthorById(id).getLastName());
		assertEquals("Date of Birth doesn't match", format.parse("01/01/1960"), aMap.getAuthorById(id).getDateOfBirth());

	}

}
