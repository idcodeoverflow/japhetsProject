package japhet.sales.service.impl;

import static japhet.sales.data.StoredProcedureParameters.*;
import static japhet.sales.data.QueryParameters.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.QueryParameters;
import japhet.sales.data.impl.UserDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.except.UnmatchedPasswordException;
import japhet.sales.model.impl.Category;
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;
import japhet.sales.util.Encryption;

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
		try {
			logger.info("Verifying user credentials: " + username);
			User user = new User();
			user.setUsername(username);
			user.setPassw(passw);
			return userDAO.doesUserExists(user);
		} catch (Exception e) {
			final String ERROR_MSG = "Error verifying user credentials: " + username;
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public User getUser(Long userId)   
			throws BusinessServiceException {
		try {
			logger.info("Obtaining user " + userId + " from the DB...");
			return userDAO.select(userId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining user " + userId + " from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean updateUser(User user)   
			throws BusinessServiceException {
		try {
			logger.info("Updating user into the DB...");
			userDAO.update(user);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error updating user into the DB: " 
					+ stringifyUser(user);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean deleteUser(User user)   
			throws BusinessServiceException {
		try {
			logger.info("Deleting user into the DB...");
			userDAO.delete(user);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error deleting user into the DB: "
					+ stringifyUser(user);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean insertUser(User user)   
			throws BusinessServiceException {
		try {
			logger.info("Inserting user into the DB...");
			userDAO.insert(user);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error inserting user into the DB: "
					+ stringifyUser(user);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public void validatePasswords(String pass1, String pass2) 
			throws InvalidPasswordException {
		try {
			logger.info("Validating password...");
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
		try {
			logger.info("Updating user categories...");
			//Generate the categories list in CSV format
			List<Category> categories = (List<Category>) params.get(P_CATEGORIES_LIST);
			String csvList = generateActiveCategoriesCSVList(categories);
			params.put(P_CATEGORIES_LIST, csvList);
			//Persist data
			userDAO.modifyUserCategories(params);
		} catch (Exception e) {
			final String ERROR_MSG = "An error has occurred while updating the user categories.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public void updateUserPassword(Map<String, Object> params) 
			throws BusinessServiceException, 
				InvalidPasswordException, 
				UnmatchedPasswordException {
		final Long UID = ((params != null 
				&& params.get(USER_ID) != null) ? (Long)params.get(USER_ID) : -1L);
		Map<String, Object> queryParams = new HashMap<>();
		try {
			logger.info("Updating user password...");
			//Set values
			final String CURRENT_PASSWORD = (String)params.get(QueryParameters.CURRENT_PASSWORD);
			final String UNCRYPTED_PASSW = (String)params.get(QueryParameters.OLD_PASSWORD);
			final String OLD_PASSWORD = Encryption.SHA256(UNCRYPTED_PASSW);
			final String CONFIRMED_PASSWORD = (String)params.get(QueryParameters.CONFIRMED_PASSWORD);
			final String PASSWORD = (String)params.get(QueryParameters.PASSW);
			//Validate that the typed password matches to the stored password
			if(!OLD_PASSWORD.equals(CURRENT_PASSWORD)) {
				final String ERROR_MSG = String
						.format("The specified password from the user: %d doesn't match.", UID);
				throw new InvalidPasswordException(ERROR_MSG);
			}
			//Validate that new password and it's confirmation matches
			if(!CONFIRMED_PASSWORD.equals(PASSWORD)) {
				final String ERROR_MSG = String
						.format("The new user:%d password and it's confirmation doesn't match.", UID);
				throw new UnmatchedPasswordException(ERROR_MSG);
			}
			//Encrypt password
			queryParams.put(QueryParameters.PASSW, Encryption.SHA256(PASSWORD));
			queryParams.put(QueryParameters.OLD_PASSWORD, OLD_PASSWORD);
			queryParams.put(QueryParameters.USER_ID, UID);
			//Persist changes
			userDAO.updateUserPassword(queryParams);
		} catch (InvalidPasswordException e) {
			throw new InvalidPasswordException();
		}  catch (UnmatchedPasswordException e) {
			throw new UnmatchedPasswordException();
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has occurred while updating a user:%d password.", UID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public User getUserByHashKey(Map<String, Object> params) 
			throws BusinessServiceException {
		User user = null;
		final String P_HASH_KEY = ((params != null 
				&& params.containsKey(HASH_KEY)) ? params.get(HASH_KEY).toString() : "-1");
		final String INFO_MSG = String.format("Obtaining User by HashKey: %s...", P_HASH_KEY);
		try {
			logger.info(INFO_MSG);
			user = userDAO.getUserByHashKey(params);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while obtaining User by HashKey: %s.", P_HASH_KEY);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return user;
	}
	
	@Override
	public User getUserByUsername(Map<String, Object> params) 
			throws BusinessServiceException {
		User user = null;
		final String P_USERNAME = ((params != null 
				&& params.containsKey(USERNAME)) ? params.get(USERNAME).toString() : "-1");
		final String INFO_MSG = String.format("Obtaining User by Username: %s...", P_USERNAME);
		try {
			logger.info(INFO_MSG);
			user = userDAO.getUserByUsername(params);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while obtaining User by Username: %s.", P_USERNAME);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return user;
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
