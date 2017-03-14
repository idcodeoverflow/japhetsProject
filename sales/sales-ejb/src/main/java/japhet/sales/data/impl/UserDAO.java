package japhet.sales.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Statuses;
import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.User;

@Stateless
public class UserDAO extends GenericDAO<User, Long> {

	@Inject
	private Logger logger;
	
	public UserDAO() {
		super(User.class, Long.class);
	}	
	
	public boolean doesUserExists(User user) 
			throws DataLayerException {
		boolean userExists = false;
		try {
			logger.info("Validating credentials...");
			List<User> users = new ArrayList<>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(USERNAME, user.getUsername());
			params.put(PASSW, user.getPassw());
			params.put(STATUS, Statuses.ACTIVE.getId());
			users = executeQuery(QueryNames.EXISTS_USER, params);
			userExists = users != null && users.size() > 0;
		} catch (Exception e) {
			final String ERROR_MSG = "Exception occurred searching the user credentials into the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return userExists;
	}
	
	public User getUserByUsername(Map<String, Object> params) 
			throws DataLayerException {
		User user = null;
		try {
			List<User> users = null;
			final String INFO_MSG = String.format("Searching details for the username: %s...", 
					params.get(USERNAME));
			logger.info(INFO_MSG);
			users = executeQuery(GET_USER_BY_EMAIL, params);
			if(users != null && !users.isEmpty()) {
				user = users.get(0);
			}
		} catch (Exception e) {
			final String ERROR_MSG = "Exception occurred searching the username into the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return user;
	}
	
	public void modifyUserCategories(Map<String, Object> params) 
			throws DataLayerException {
		Long userId = null;
		String csvUserCategories = null;
		try {
			logger.info("Modifying user categories...");
			userId = (Long)params.get(P_USER_ID);
			csvUserCategories = (String)params.get(P_CATEGORIES_LIST);
			executeStoredProcedure(CHANGE_USER_CATEGORIES_NAME, params);
		} catch (Exception e) {
			String ERROR_MSG = String.format("An error has ocurred modifying the user %d categories: %s", 
					userId, csvUserCategories);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}
	
	public void updateUserPassword(Map<String, Object> params) 
			throws DataLayerException {
		final long USER_ID_PARAM = ((params != null 
				&& params.get(USER_ID) != null) ? (Long)params.get(USER_ID) : -1);
		final String INFO_MSG = String.format("Updating the password from the User: %d...", USER_ID_PARAM);
		try {
			logger.info(INFO_MSG);
			executeUpdate(UPDATE_USER_PASSWORD, params);
		} catch (Exception e) {
			String ERROR_MSG = String
					.format("An error has ocurred update the password from the User: %d.", USER_ID_PARAM);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}
}
