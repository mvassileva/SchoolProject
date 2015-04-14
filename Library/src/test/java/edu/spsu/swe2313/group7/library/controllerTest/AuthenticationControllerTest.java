package edu.spsu.swe2313.group7.library.controllerTest;

import edu.spsu.swe2313.group7.library.controller.AuthenticationController;
import edu.spsu.swe2313.group7.library.model.NameAndPassword;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
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
public class AuthenticationControllerTest {
	
	
	@Autowired
	private AuthenticationController authController;
	
	@BeforeClass
	public static void setUpClass()
	{

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
		
	}

}
