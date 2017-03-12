package japhet.sales.data.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.UserSearch;

@Stateless
public class UserSearchDAO extends GenericDAO<UserSearch, Long> {

	@Inject
	private Logger logger;
	
	public UserSearchDAO() {
		super(UserSearch.class, Long.class);
	}
	
	public List<UserSearch> getUserSearchByUser(Map<String, Object> params) 
			throws DataLayerException {
		List<UserSearch> userSearchs = null;
		try {
			logger.info("Obtaining user search by user...");
			userSearchs = executeQuery(GET_USER_SEARCH_BY_USER, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining user search by user.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return userSearchs;
	}

}
