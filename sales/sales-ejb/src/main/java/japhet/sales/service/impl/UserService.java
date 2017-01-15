package japhet.sales.service.impl;

import static japhet.sales.data.StoredProcedureParameters.*;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.model.impl.Category;
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
	public static final String JNDI_BINDING = 
			"java:app/sales-ejb/UserService!japhet.sales.service.impl.UserService";

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
			final String errorMsg = "Error verifying user credentials: " + username;
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public User getUser(Long userId)   
			throws BusinessServiceException {
		logger.info("Obtaining user " + userId + " from the DB...");
		try {
			return userDAO.select(userId);
		} catch (Exception e) {
			final String errorMsg = "Error obtaining user " + userId + " from the DB.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public boolean updateUser(User user)   
			throws BusinessServiceException {
		logger.info("Updating user into the DB...");
		try {
			userDAO.update(user);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error updating user into the DB: " 
					+ stringifyUser(user);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public boolean deleteUser(User user)   
			throws BusinessServiceException {
		logger.info("Deleting user into the DB...");
		try {
			userDAO.delete(user);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error deleting user into the DB: "
					+ stringifyUser(user);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public boolean insertUser(User user)   
			throws BusinessServiceException {
		logger.info("Inserting user into the DB...");
		try {
			userDAO.insert(user);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error inserting user into the DB: "
					+ stringifyUser(user);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void modifyUserCategories(Map<String, Object> params) 
			throws BusinessServiceException {
		logger.info("Updating user categories...");
		try {
			//Generate the categories list in CSV format
			List<Category> categories = (List<Category>) params.get(P_CATEGORIES_LIST);
			String csvList = generateActiveCategoriesCSVList(categories);
			params.put(P_CATEGORIES_LIST, csvList);
			//Persist data
			userDAO.modifyUserCategories(params);
		} catch (Exception e) {
			final String ERROR_MSG = "An error has ocurred while updating the user categories.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	private Long stringifyUser(User user) {
		return ((user != null && user.getUserId() != null) ? 
				user.getUserId() : null);
	}
	
	private String generateActiveCategoriesCSVList(List<Category> categories) {
		StringBuilder csv = new StringBuilder("");
		if(categories != null) {
			short index = 0;
			for(Category category : categories) {
				if(category.getChecked()) {
					csv.append(category.getCategoryId());
					if(categories.size() > ++index) {
						csv.append(",");
					}
				}
			}
		}
		return csv.toString();
	}
}
