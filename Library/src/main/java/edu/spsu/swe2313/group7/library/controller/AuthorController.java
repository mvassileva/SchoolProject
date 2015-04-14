package edu.spsu.swe2313.group7.library.controller;

import edu.spsu.swe2313.group7.library.dao.AuthorMapper;
import edu.spsu.swe2313.group7.library.model.Author;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostConstruct
	public void init() {
		//throw around some test books
		//authorList = new ArrayList(300);
		dummyDataLoad();
	}

	public void setAuthorMapper(AuthorMapper mapper) {
		this.authorMapper = mapper;
	}

	private void dummyDataLoad() {
		for (int i = 0; i < 300; i++) {
			Author a = new Author();
			a.setLastName("Last" + i);
			a.setFirstName("First" + i);
			authorMapper.addAuthor(a);

		}
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
	public Author createAuthor(@RequestBody Author a) {
		authorMapper.addAuthor(a);
		return a;

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
