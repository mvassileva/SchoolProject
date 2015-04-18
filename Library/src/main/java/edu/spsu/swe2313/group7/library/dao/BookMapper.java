package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.BookStatus;
import edu.spsu.swe2313.group7.library.model.User;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transaction;
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
	
	@Transactional
	public Long addBook(Book b) {
		/*
		if (b.getAuthors() != null ) {
			for (Author a : b.getAuthors() ) {
				//TODO Fix up many to many in both directions
				Author managedA = em.merge(a);
				managedAuthors.add(managedA);
				//a.setId(0);
				em.persist(a);
			}
		}
		if (b.getWaitingList() != null) {
			//TODO Fix up many to many in both directions
			for (User p : b.getWaitingList()) {
				em.persist(p);
			}
		}
		*/
		if (b.getCheckedOutBy()!= null) {
			em.persist(b.getCheckedOutBy());
		}
		if (b.getStatus() == null) {
			b.setStatus(BookStatus.CHECKEDIN);
		}
		if (b.getCheckOutDuration() == 0) {
			b.setCheckOutDuration(defaultCheckoutDuration);
		}
		//em.persist(b);
		
		Book savedB = em.merge(b);
		logger.info("Book saved successfuly, " + savedB.getTitle());
		return 	savedB.getId();
	}

	public void updateBook(Book b) {
		Book managedB = em.merge(b);
		em.persist(managedB);
		logger.info("Book updated successfuly, " + managedB.getTitle());
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
	@Transactional
	public boolean checkOut(Book b, User u) {
		logger.debug("Starting Checkout process");
		Book managedB = em.merge(b);
		User managedU = em.merge(u);
		if (managedB == null || managedU == null ) {
			//unable to move book or user to this
			//persistence context
			logger.error("Unable to locate User or book in this persistence context");
			return false;
		}
		
		//Make a new date for right now, add the number of 
		//days for the book's checkout duration to it, and then set it.
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, b.getCheckOutDuration());
	
		managedB.setDueDate(cal.getTime());
		managedB.setStatus(BookStatus.CHECKEDOUT);
		managedB.setCheckedOutBy(managedU);
		if (!managedU.getBooksCheckedOut().contains(managedB)) {
			managedU.getBooksCheckedOut().add(managedB);
		}
		em.persist(managedU);
		em.persist(managedB);
		logger.debug("Successfully completed Checkout");
		return true;
	}
	
	@Transactional
	public boolean checkIn(Book b) {
		logger.debug("Starting CheckIn process");
		Book managedB = em.merge(b);
		User managedU = managedB.getCheckedOutBy();
		
		if (managedB == null ) {
			//unable to move book to this
			//persistence context
			logger.error("Unable to locate book in this persistence context");
			return false;
		}
		
		//remove book from user's checkout list
		if (managedU != null && managedU.getBooksCheckedOut() != null 
			&& managedU.getBooksCheckedOut().size() > 0) {
			managedU.getBooksCheckedOut().remove(managedB);
		}
		managedB.setCheckedOutBy(null);
		managedB.setDueDate(null);
		managedB.setStatus(BookStatus.CHECKEDIN);
		//Notify Waitling list?
		em.persist(managedB);
		if (managedB.getCheckedOutBy() == null) {
			logger.debug("Successfully completed CheckIn");
			return true;
		}
		logger.debug("Checkin Failed");
		return false;
	}
	
	@Transactional
	public boolean addToWaitlist(Book b, User u) {
		logger.debug("Starting Add to Waitlist");
		Book managedB = em.merge(b);
		User managedU = em.merge(u);
		
		if (managedB == null || managedU == null ) {
			//unable to move book or user to this
			//persistence context
			logger.error("Unable to locate User or book in this persistence context");
			return false;
		}
		
		if (managedB.getWaitingList() != null) {
			managedB.getWaitingList().add(managedU);
			if (!managedU.getWaitingListBooks().contains(managedB)) {
				managedU.getWaitingListBooks().add(managedB);
			}
			em.persist(managedB);
			em.persist(managedU);
			logger.debug("Successfully completed WaitList addition");
			return true;
		}
		//something went wrong
		logger.debug("Failed to add user to waitling list");
		return false;
	} 
	
	@Transactional
	public boolean removeFromWaitlist(Book b, User u) {
		logger.debug("Starting Remove From waitlist");
		Book managedB = em.merge(b);
		User managedU = em.merge(u);
		
		if (managedB == null || managedU == null ) {
			//unable to move book or user to this
			//persistence context
			logger.error("Unable to locate User or book in this persistence context");
			return false;
		}
		
		if (managedB.getWaitingList() != null) {
			if (managedB.getWaitingList().contains(managedU)) {
				managedB.getWaitingList().remove(managedU);
				em.persist(managedB);
				
				if (managedU.getWaitingListBooks().contains(managedB)) {
					managedU.getWaitingListBooks().remove(managedB);
				}
				
				logger.debug("Successfully completed WaitList removal");
				return true;
			} else {
				logger.debug("User wasn't found on waiting list, unable to remove, method returning true as user is not on the waiting list");
				return true;
			}
		}
		//something went wrong
		logger.debug("Failed to remove user from waitling list");
		return false;
	}
}
