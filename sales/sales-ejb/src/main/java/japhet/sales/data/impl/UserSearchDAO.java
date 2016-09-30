package japhet.sales.data.impl;

import static japhet.sales.data.QueryNames.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.UserSearch;

@Stateless
public class UserSearchDAO extends GenericDAO<UserSearch, Long> {

	@Inject
	private Logger logger;
	
	public UserSearchDAO() {
		super(UserSearch.class, Long.class);
	}
	
	public List<UserSearch> getUserSearchByUser(Map<String, Object> params) {
		List<UserSearch> userSearchs = null;
		logger.info("Obtaining user search by user...");
		try {
			userSearchs = executeQuery(GET_USER_SEARCH_BY_USER, params);
		} catch (Exception e) {
			logger.severe("Error obtaining user search by user.\n" 
					+ e.getStackTrace());
		}
		return userSearchs;
	}

}
