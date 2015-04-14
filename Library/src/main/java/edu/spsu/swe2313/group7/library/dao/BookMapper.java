package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Robert Whitaker
 */
@Transactional
@Service("bookMapper")
public class BookMapper {
	private static final Logger logger = Logger.getLogger(BookMapper.class);
	
	//TODO: properties file
	private int defaultCheckoutDuration = 14;
	
	@PersistenceContext
	private EntityManager em;
	
	public Long addBook(Book b) {
		if (b.getAuthors() != null ) {
			for (Author a : b.getAuthors() ) {
				a.setId(0);
				em.persist(a);
			}
		}
		if (b.getWaitingList() != null) {
			for (User p : b.getWaitingList()) {
				em.persist(p);
			}
		}
		
		if (b.getCheckedOutBy()!= null) {
			em.persist(b.getCheckedOutBy());
		}
		if (b.getStatus() == null) {
			b.setStatus(BookStatus.CHECKEDIN);
		}
		if (b.getCheckOutDuration() == 0) {
			b.setCheckOutDuration(defaultCheckoutDuration);
		}
		em.persist(b);
		logger.info("Book saved successfuly, " + b.getTitle());
		return 	b.getId();
	}

	public void updateBook(Book b) {
		em.refresh(b);
		logger.info("Book updated successfuly, " + b.getTitle());
	}
	
	public Book getBookById(long id) {
		try {
			logger.debug("Trying to locate book with the id:" + id);
			Book b = (Book) em.find(Book.class, id);
			logger.info("Book loaded successfully, " + b.getTitle());
			if (b.getAuthors() != null) { 
				for (Author a : b.getAuthors()) {
					logger.debug("Author " + a.getFirstName() + " " + a.getLastName() + " Loaded");
				}
			}
			return b; 
		} catch (Exception ex) {
			logger.error("Exception:",ex );
			return null;
		}
		//return null;
	}
	
	
	public List<Book> getBookByTitle(String title) {
		try {
			logger.debug("Trying to locate book with the title:" + title);
			List<Book> bList = em.createQuery("select b from Book b where b.title like :bookTitle").setParameter("bookTitle", title).getResultList();
			logger.debug("Returning list of books");
			return  bList; 
		} catch (Exception ex) {
			logger.error("Exception:",ex );
			return null;
		}
		//return null;
	}
	
	public List<Book> getBooks() {
		List<Book> bList = em.createQuery("select b from Book b", Book.class).getResultList();
		logger.error("LIST LENGTH " + bList.size());
		return bList;
		//return null;
	}
	
	public void removeBook(long id) {   
		Book b = (Book) em.find(Book.class, id);
		if (b != null) {
			em.remove(b);
			logger.info("Book Successfully Deleted, " + b.getTitle());
			return;
		}
		logger.error("Delete: Unable to find book with id:" + id);
		
	}
	
	public List<Book> getBooksByAuthor(long authorId) {
		//TODO: wire up by author search
		List<Book> bList = em.createQuery("select b from Book b", Book.class).getResultList();
		logger.error("LIST LENGTH " + bList.size());
		return bList;
	}
	
	public List<Book> getOverDueBooks() {
	//TODO: wire up by author search
	List<Book> bList = em.createQuery("select b from Book b where b.dueDate < CURRENT_DATE ", Book.class).getResultList();
	logger.error("LIST LENGTH " + bList.size());
	return bList;
	}

}
