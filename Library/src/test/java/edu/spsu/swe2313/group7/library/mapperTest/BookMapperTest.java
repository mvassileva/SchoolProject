package edu.spsu.swe2313.group7.library.MapperTest;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

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
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.AuthenticationToken;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author lr3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class BookMapperTest
{

	private static SimpleDateFormat format;

	@Autowired
	@Qualifier("bookMapper")
	private BookMapper bookMapper;

	@Autowired
	@Qualifier("authorMapper")
	private AuthorMapper authorMapper;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("authMapper")
	private AuthenticationMapper authMapper;

	public BookMapperTest() {
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
		assertNotNull("Book Mapper DAO object is null, unable to complete further testsing", bookMapper);
		assertNotNull("Author Mapper DAO object is null, unable to complete further testsing", authorMapper);
	}

	@Test
	public void addBookTest() throws ParseException
	{
		// Book test, our text book :)

		Author a1 = new Author();
		a1.setLastName("Tsui");
		a1.setFirstName("Frank");

		Author a2 = new Author();
		a2.setLastName("Karam");
		a2.setFirstName("Orlando");

		Author a3 = new Author();
		a3.setLastName("Bernal");
		a3.setFirstName("Barbara");
		
		List<Author> aList = new LinkedList<>();

		aList.add(a1);
		aList.add(a2);
		aList.add(a3);

		Book b = new Book();
		b.setISBN13("978-1449691998");
		b.setISBN10("1449691994");
		b.setTitle("Essentials Of Software Engineering");
		b.setStatus(BookStatus.CHECKEDIN);
		b.setAuthors(aList);
		b.setPublishDate(format.parse("2013-02-13"));

		Long id = bookMapper.addBook(b);

		assertEquals("ISBN13 Doesn't match Expected!", "978-1449691998", bookMapper.getBookById(id).getISBN13());
		assertEquals("ISBN10 Doesn't match Expected!", "1449691994", bookMapper.getBookById(id).getISBN10());
		assertEquals("Title Doesn't match Expected!", "Essentials Of Software Engineering", bookMapper.getBookById(id).getTitle());
		assertEquals("Book Status Doesn't match Expected!", BookStatus.CHECKEDIN, bookMapper.getBookById(id).getStatus());
		assertEquals("Author 1 First Name  Doesn't match Expected!", "Frank", bookMapper.getBookById(id).getAuthors().get(0).getFirstName());
		assertEquals("Author 2 First Name  Doesn't match Expected!", "Orlando", bookMapper.getBookById(id).getAuthors().get(1).getFirstName());
		assertEquals("Author 3 First Name  Doesn't match Expected!", "Barbara", bookMapper.getBookById(id).getAuthors().get(2).getFirstName());
		assertEquals("Author 1 Last Name  Doesn't match Expected!", "Tsui", bookMapper.getBookById(id).getAuthors().get(0).getLastName());
		assertEquals("Author 2 Last Name  Doesn't match Expected!", "Karam", bookMapper.getBookById(id).getAuthors().get(1).getLastName());
		assertEquals("Author 3 Last Name  Doesn't match Expected!", "Bernal", bookMapper.getBookById(id).getAuthors().get(2).getLastName());
		assertEquals("Publisher Date Doesn't match Expected!", format.parse("2013-02-13"), bookMapper.getBookById(id).getPublishDate());

	}
	
	@Test
	public void testCheckOut() throws ParseException{
		//Preload Data
		preload();
		User pat = userMapper.getUserByName("patron1");
		List<Book> b1 = bookMapper.getBookByTitle("Pride and Prejudice");
		assertNotNull("Couldn't find any books named Pride and Prejudice", b1);
		
		for (Book b : b1) {
			assertNotNull("Book was null!",b);
			assertTrue("Checkout Failed, bookMapper returned false", bookMapper.checkOut(b, pat));
		}
		
	}
	
	@Test
	public void testCheckIn() throws ParseException {
		preload();
		List<Book> b1 = bookMapper.getBookByTitle("Pride and Prejudice");
		assertNotNull("Couldn't find any books named Pride and Prejudice", b1);
		
		for (Book b : b1) {
			assertNotNull("Book was null!",b);
			assertTrue("CheckIn Failed, bookMapper returned false", bookMapper.checkIn(b));
		}
	}
	
	private void preload() throws ParseException {
		
		User patron1 = new User();
		patron1.setUserName("patron1");
		patron1.setLastName("One");
		patron1.setFirstName("Patron");
		patron1.setEmailAddress("test@example.com");
		patron1.setUserLevel(UserLevel.PATRON);
		userMapper.addUser(patron1);
		
		Author auth1 = new Author();
		auth1.setFirstName("Jane");
		auth1.setLastName("Austen");
		Book book1 = new Book();
		book1.setAuthor(auth1);
		book1.setTitle("Pride and Prejudice");
		book1.setPublishDate(format.parse("1813-01-01"));
		bookMapper.addBook(book1);
		
		Author auth2 = new Author();
		auth2.setFirstName("Harper");
		auth2.setLastName("Lee");
		Book book2 = new Book();
		book2.setAuthor(auth2);
		book2.setTitle("To Kill a Mockingbird");
		book2.setPublishDate(format.parse("1960-01-01"));
		bookMapper.addBook(book2);
		
		Author auth3 = new Author();
		auth3.setFirstName("F. Scott");
		auth3.setLastName("Fitzgerald");
		Book book3 = new Book();
		book3.setAuthor(auth3);
		book3.setTitle("The Great Gatsby");
		book3.setPublishDate(format.parse("1925-01-01"));
		bookMapper.addBook(book3);
		
		Author auth4 = new Author();
		auth4.setFirstName("Charlotte");
		auth4.setLastName("Bronte");
		Book book4 = new Book();
		book4.setAuthor(auth4);
		book4.setTitle("Jane Eyre");
		book4.setPublishDate(format.parse("1847-01-02"));
		bookMapper.addBook(book4);
		
		Author auth5 = new Author();
		auth5.setFirstName("George");
		auth5.setLastName("Orwell");
		Book book5 = new Book();
		book5.setAuthor(auth5);
		book5.setTitle("1984");
		book5.setPublishDate(format.parse("1948-01-01"));
		bookMapper.addBook(book5);
		
		Author auth6 = new Author();
		auth6.setFirstName("J.D.");
		auth6.setLastName("Salinger");
		Book book6 = new Book();
		book6.setAuthor(auth6);
		book6.setTitle("The Catcher in the Rye");
		book6.setPublishDate(format.parse("1951-01-01"));
		bookMapper.addBook(book6);
		
		Book book7 = new Book();
		book7.setAuthor(auth5);
		book7.setTitle("Animal Farm");
		book7.setPublishDate(format.parse("1945-01-01"));
		bookMapper.addBook(book7);
		
		Author auth8 = new Author();
		auth8.setFirstName("Emily");
		auth8.setLastName("Bronte");
		Book book8 = new Book();
		book8.setAuthor(auth8);
		book8.setTitle("Wuthering Heights");
		book8.setPublishDate(format.parse("1847-01-01"));
		bookMapper.addBook(book8);
		
		Author auth9 = new Author();
		auth9.setFirstName("Louisa May");
		auth9.setLastName("Alcott");
		Book book9 = new Book();
		book9.setAuthor(auth9);
		book9.setTitle("Little Women");
		book9.setPublishDate(format.parse("1868-01-01"));
		bookMapper.addBook(book9);
		
		Author auth10 = new Author();
		auth10.setFirstName("William");
		auth10.setLastName("Golding");
		Book book10 = new Book();
		book10.setAuthor(auth10);
		book10.setTitle("Lord of the Flies");
		book10.setPublishDate(format.parse("1954-01-01"));
		bookMapper.addBook(book10);
		
	}

}
