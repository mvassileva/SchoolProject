package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthenticationMapper;
import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.dao.UserMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.UserLevel;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Robert Whitaker
 */
@Controller
@RequestMapping("/author")
public class AuthorController {

	private static final Logger logger = Logger.getLogger(AuthorController.class);

	@Autowired
	@Qualifier("authorMapper")
	private AuthorMapper authorMapper;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("authMapper")
	private AuthenticationMapper authMapper;
	
	@PostConstruct
	public void init() {
	}

	public void setAuthorMapper(AuthorMapper mapper) {
		this.authorMapper = mapper;
	}

	@RequestMapping(value = "",
		method = RequestMethod.GET,
		produces = "application/json")
	public @ResponseBody
	List<Author> getAuthorList(ModelMap model) {
		return authorMapper.getAuthors();
	}

	@RequestMapping(value = "",
		method = RequestMethod.POST)
	@ResponseBody
	public Author createAuthor(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @RequestBody Author a) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.LIBRARIAN)) {
			authorMapper.addAuthor(a);
			return a;
		}
		return null;
	}

	@RequestMapping(value = "{authorId}",
		method = RequestMethod.PUT)
	@ResponseBody
	public Author updateAuthor(@RequestHeader("API-User") String userName, @RequestHeader("API-Key") String key, @RequestBody Author a, @PathVariable Long authorId) {
		if (authMapper.verifyUserAccessLevel(userName, key, UserLevel.LIBRARIAN)) {
			authorMapper.updateAuthor(a);
			return a;
		}
		return null;
	}
	
	
	@RequestMapping(value = "{authorId}",
		method = RequestMethod.GET,
		produces = "application/json")
	public @ResponseBody
	Author findAuthorById(@PathVariable long authorId) {
		logger.debug("Called Find Author By Id");
		Author a = authorMapper.getAuthorById(authorId);
		logger.debug("Author Object loaded!");
		logger.debug("Author Name:" + a.getFirstName() + " " + a.getLastName());
		logger.debug("Author ID = " + a.getId());

		return (a);
	}
}
