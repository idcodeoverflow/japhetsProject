package japhet.sales.service.impl;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;

import org.apache.log4j.Logger;

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
	public boolean doesUserExists(String username, String passw)   
			throws BusinessServiceException {
		logger.info("Verifying user credentials: " + username);
		try {
			User user = new User();
			user.setUsername(username);
			user.setPassw(passw);
			return userDAO.doesUserExists(user);
		} catch (Exception e) {
			logger.fatal("Error verifying user credentials: " + username, e);
		}
		return false;
	}
	
	@Override
	public User getUser(Long userId)   
			throws BusinessServiceException {
		logger.info("Obtaining user " + userId + " from the DB...");
		try {
			return userDAO.select(userId);
		} catch (Exception e) {
			logger.fatal("Error obtaining user " + userId + " from the DB.", e);
		}
		return null;
	}
	
	@Override
	public boolean updateUser(User user)   
			throws BusinessServiceException {
		logger.info("Updating user into the DB...");
		try {
			userDAO.update(user);
			return true;
		} catch (Exception e) {
			logger.fatal("Error updating user into the DB.", e);
		}
		return false;
	}
	
	@Override
	public boolean deleteUser(User user)   
			throws BusinessServiceException {
		logger.info("Deleting user into the DB...");
		try {
			userDAO.delete(user);
			return true;
		} catch (Exception e) {
			logger.fatal("Error deleting user into the DB.", e);
		}
		return false;
	}
	
	@Override
	public boolean insertUser(User user)   
			throws BusinessServiceException {
		logger.info("Inserting user into the DB...");
		try {
			userDAO.insert(user);
			return true;
		} catch (Exception e) {
			logger.fatal("Error inserting user into the DB.", e);
		}
		return false;
	}
	
	@Override
	public void validatePasswords(String pass1, String pass2) 
			throws InvalidPasswordException {
		logger.info("Validating password...");
		try {
			if(pass1.length() < MINIMUM_PASSWORD_LENGTH){
				throw new InvalidPasswordException("The length of the password is lower than the minimum.");
			}
			if(!pass1.equals(pass2)){
				throw new InvalidPasswordException("The passwords doesn't match.");
			}
		} catch (Exception e) {
			logger.fatal("Error validating the password.", e);
			throw new InvalidPasswordException("Invalid passwords.");
		}
	}
}
