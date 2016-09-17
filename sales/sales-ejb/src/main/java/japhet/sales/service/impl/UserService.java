package japhet.sales.service.impl;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserDAO;
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;

@LocalBean
@Stateless
public class UserService implements IUserService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -9215801294999528333L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserDAO userDAO;

	@Override
	public boolean doesUserExists(String username, String passw) {
		logger.info("Verifying user credentials: " + username);
		try {
			User user = new User();
			user.setUsername(username);
			user.setPassw(passw);
			return userDAO.doesUserExists(user);
		} catch (Exception e) {
			logger.severe("Error verifying user credentials: " + username 
					+ "\n" + e.getStackTrace());
		}
		return false;
	}
	
	@Override
	public User getUser(Long userId) {
		logger.info("Obtaining user " + userId + " from the DB...");
		try {
			return userDAO.select(userId);
		} catch (Exception e) {
			logger.severe("Error obtaining user " + userId + " from the DB.");
		}
		return null;
	}
	
	@Override
	public boolean updateUser(User user) {
		logger.info("Updating user into the DB...");
		try {
			userDAO.update(user);
			return true;
		} catch (Exception e) {
			logger.severe("Error updating user into the DB.");
		}
		return false;
	}
	
	@Override
	public boolean deleteUser(User user) {
		logger.info("Deleting user into the DB...");
		try {
			userDAO.delete(user);
			return true;
		} catch (Exception e) {
			logger.severe("Error deleting user into the DB.");
		}
		return false;
	}
	
	@Override
	public boolean insertUser(User user) {
		logger.info("Inserting user into the DB...");
		try {
			userDAO.insert(user);
			return true;
		} catch (Exception e) {
			logger.severe("Error inserting user into the DB.");
		}
		return false;
	}
	
}
