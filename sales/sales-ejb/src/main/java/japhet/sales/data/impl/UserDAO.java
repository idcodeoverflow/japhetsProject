package japhet.sales.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.User;
import japhet.sales.util.StringUtils;

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
			params.put("username", user.getUsername());
			params.put("passw", user.getPassw());
			users = executeQuery(QueryNames.EXISTS_USER, params);
			userExists = users != null && users.size() > 0;
		} catch (Exception e) {
			final String errorMsg = "Exception occurred searching the user credentials into the DB.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
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
			final String errorMsg = "Exception occurred searching the username into the DB.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public void modifyUserCategories(Map<String, Object> params) 
			throws DataLayerException {
		Long userId = null;
		String csvUserCategories = null;
		List<Short> userCategories = null;
		try {
			logger.info("Modifying user categories...");
			userId = (Long)params.get(P_USER_ID);
			userCategories = (List<Short>)params.get(P_CATEGORIES_LIST);
			csvUserCategories = StringUtils.listToCSVString(userCategories);
			executeStoredProcedure(CHANGE_USER_CATEGORIES_NAME, params);
		} catch (Exception e) {
			String ERROR_MSG = String.format("An error has ocurred modifying the user %d categories: %s", 
					userId, csvUserCategories);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}
}
