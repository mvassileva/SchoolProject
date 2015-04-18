package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.Author;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Robert Whitaker
 */
@Transactional
@Service("authorMapper")
public class AuthorMapper {
	private static final Logger logger = Logger.getLogger(AuthorMapper.class);
	
	@PersistenceContext
	private EntityManager em;
	
	
	public Long addAuthor(Author a) {
		em.persist(a);
		logger.info("Author saved successfuly, " + a.getLastName() + ", " + a.getFirstName());
		return 	a.getId();
	}

	public void updateAuthor(Author a) {
		Author managedA = em.merge(a);
		em.persist(managedA);
		logger.info("Author updated successfuly, " + managedA.getLastName() + ", " + managedA.getFirstName());
	}
	
	public Author getAuthorById(long id) {   
		Author a = (Author) em.find(Author.class, id);
		logger.info("Author loaded successfully, " + a.getLastName() + ", " + a.getFirstName());
		logger.debug("Author Object loaded!");
		logger.debug("Author Name:" + a.getFirstName() + " " + a.getLastName());
		logger.debug("Author ID = " + a.getId());
		return a;
		//return null;
	}
	
	public List<Author> getAuthors() {
		List<Author> aList = em.createQuery("select a from Author a", Author.class).getResultList();
		return aList;
		//return null;
	}
	
	public void removeAuthor(long id) {   
		Author a = (Author) em.find(Author.class, id);
		if (a != null) {
			em.remove(a);
			logger.info("Author Successfully Deleted, " + a.getLastName() + ", " + a.getFirstName());
			return;
		}
		logger.error("Delete: Unable to find Author with id:" + id);
		
	}

}
