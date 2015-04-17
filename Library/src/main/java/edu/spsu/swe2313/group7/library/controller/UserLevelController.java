package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Robert Whitaker
 */
@Controller
@RequestMapping("/userLevels")
public class UserLevelController {
	@RequestMapping( value = "",
	method = RequestMethod.GET)
	public @ResponseBody List<UserLevel> getOverDueBooks() {
		List<UserLevel> responseList = new LinkedList<>();
		responseList.add(UserLevel.ADMINISTRATOR);
		responseList.add(UserLevel.LIBRARIAN);
		responseList.add(UserLevel.JRLIBRARIAN);
		responseList.add(UserLevel.OTHERSTAFF);
		responseList.add(UserLevel.PATRON);
		responseList.add(UserLevel.NOACCESS);
		return responseList;
		
	}
}
