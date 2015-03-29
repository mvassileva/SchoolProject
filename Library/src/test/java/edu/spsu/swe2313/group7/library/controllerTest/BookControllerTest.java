/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.spsu.swe2313.group7.library.controllerTest;

import edu.spsu.swe2313.group7.library.controller.BookController;
import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.dao.BookMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author lr3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/library-context.xml")
public class BookControllerTest {
	
	private static SimpleDateFormat format;
		
	@Autowired
	@Qualifier("bookMapper")
	private BookMapper bMap;
	
	@Autowired
	@Qualifier("authorMapper")
	private AuthorMapper aMap;
	
	public BookControllerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		format = new SimpleDateFormat("YYYY-MM-DD");
		
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void bogusSpringTest() {
		assertNotNull("Book Mapper DAO object is null, unable to complete further testsing", bMap);
		assertNotNull("Author Mapper DAO object is null, unable to complete further testsing", aMap);
	}
	
	
	
	
	@Test
	public void addBookTest() throws ParseException {
		//Book test, our text book :)
		
		Author a1 = new Author();
		a1.setLastName("Tsui");
		a1.setFirstName("Frank");
		//a1.setId(1);
		
		Author a2 = new Author();
		a2.setLastName("Karam");
		a2.setFirstName("Orlando");
		//a2.setId(2);
		
		Author a3 = new Author();
		a3.setLastName("Bernal");
		a3.setFirstName("Barbara");
		//a3.setId(3);
		List<Author> aList = new LinkedList<>();
		

		
		//aMap.addAuthor(a1);
		//System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
		//	+ "Author DD : "+ a1.getId() +
		//	"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		//aMap.addAuthor(a2);
		//aMap.addAuthor(a3);
			
		
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
		
		
		Long id = bMap.addBook(b);
		
		assertEquals("ISBN13 Doesn't match Expected!", "978-1449691998", bMap.getBookById(id).getISBN13());
		
		
		
		
	}
	
}
