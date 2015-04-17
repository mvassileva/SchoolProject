package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.model.BookStatus;
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
@RequestMapping("/bookStatus")
public class BookStatusController {
	@RequestMapping( value = "",
	method = RequestMethod.GET)
	public @ResponseBody List<BookStatus> getBookStatuses() {
		List<BookStatus> responseList = new LinkedList<>();
		responseList.add(BookStatus.CHECKEDIN);
		responseList.add(BookStatus.CHECKEDOUT);
		responseList.add(BookStatus.HOLD);
		responseList.add(BookStatus.LATE);
		responseList.add(BookStatus.LOST);
		responseList.add(BookStatus.RESERVE);
		return responseList;
		
	}
}
