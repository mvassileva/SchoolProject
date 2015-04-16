package edu.spsu.swe2313.group7.library.dao;

import edu.spsu.swe2313.group7.library.model.Author;
import edu.spsu.swe2313.group7.library.model.Book;
import edu.spsu.swe2313.group7.library.model.User;
import edu.spsu.swe2313.group7.library.model.UserLevel;
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
@Service("userMapper")
public class UserMapper {
	private static final Logger logger = Logger.getLogger(UserMapper.class);
	
	@PersistenceContext
	private EntityManager em;
	
	public Long addUser(User u) {
		if (u.getBookCheckoutLimit() == 0 
			&& u.getUserLevel() == UserLevel.PATRON
			&& u.isAllowedCheckout()) {
			u.setBookCheckoutLimit(5);
		}
		em.persist(u);
		logger.info("User saved successfuly, " + u.getLastName() + ", " + u.getFirstName());
		return 	u.getId();
	}

	public void updateUser(User u) {
		em.refresh(u);
		logger.info("User updated successfuly, " + u.getLastName() + ", " + u.getFirstName());
	}
	
	public User getUserById(long id) {   
		User u = (User) em.find(User.class, id);
		logger.info("User loaded successfully, " + u.getLastName() + ", " + u.getFirstName());
		return u;
	}
	
	public List<User> getUsers() {
		List<User> pList = em.createQuery("select p from User p", User.class).getResultList();
		logger.error("LIST LENGTH " + pList.size());
		return pList;
		//return null;
	}
	
	public List<User> getUsersWithFines() {
	List<User> pList = em.createQuery("select p from User p where lateFees > 0", User.class).getResultList();
	logger.error("LIST LENGTH " + pList.size());
	return pList;
	//return null;
	}
	
	public void removeUser(long id) {   
		User u = (User) em.find(User.class, id);
		if (u != null) {
			em.remove(u);
			logger.info("User Successfully Deleted, " + u.getLastName() + ", " + u.getFirstName());
			return;
		}
		logger.error("Delete: Unable to find User with id:" + id);
		
	}
	
	public User getUserByUserName(String userName) {
		List<User> ulist = em.createQuery("select u from User u where u.userName = :userName").setParameter("userName", userName).getResultList();
		for (User u : ulist) {
			logger.info("User loaded successfully, " + u.getLastName() + ", " + u.getFirstName());
			return u;
		}
		logger.error("User with name, " + userName + " not found.");
		return null;
	}
}
