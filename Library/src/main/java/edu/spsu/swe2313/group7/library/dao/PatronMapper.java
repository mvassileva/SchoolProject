package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.Patron;
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
@Service("patronMapper")
public class PatronMapper {
	private static final Logger logger = Logger.getLogger(PatronMapper.class);
	
	@PersistenceContext
	private EntityManager em;
	
	public Long addPatron(Patron p) {
		em.persist(p);
		logger.info("Patron saved successfuly, " + p.getLastName() + ", " + p.getFirstName());
		return 	p.getId();
	}

	public void updatePatron(Patron p) {
		em.refresh(p);
		logger.info("Patron updated successfuly, " + p.getLastName() + ", " + p.getFirstName());
	}
	
	public Patron getPatronById(long id) {   
		Patron p = (Patron) em.find(Patron.class, id);
		logger.info("Book loaded successfully, " + p.getLastName() + ", " + p.getFirstName());
		return p;
	}
	
	public List<Patron> getPatrons() {
		List<Patron> pList = em.createQuery("select p from Patron p", Patron.class).getResultList();
		logger.error("LIST LENGTH " + pList.size());
		return pList;
		//return null;
	}
	
	public List<Patron> getPatronsWithFines() {
	List<Patron> pList = em.createQuery("select p from Patron p where lateFees > 0", Patron.class).getResultList();
	logger.error("LIST LENGTH " + pList.size());
	return pList;
	//return null;
	}
	
	public void removePatron(long id) {   
		Patron p = (Patron) em.find(Patron.class, id);
		if (p != null) {
			em.remove(p);
			logger.info("Patron Successfully Deleted, " + p.getLastName() + ", " + p.getFirstName());
			return;
		}
		logger.error("Delete: Unable to find Patron with id:" + id);
		
	}
}
