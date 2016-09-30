package japhet.sales.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserSearchDAO;
import japhet.sales.model.impl.UserSearch;
import japhet.sales.service.IUserSearch;

@LocalBean
@Stateless
public class UserSearchService implements IUserSearch {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -6273934734754835975L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserSearchDAO userSearchDAO;

	@Override
	public void insertUserSearch(UserSearch userSearch) {
		logger.info("Saving user search...");
		try {
			userSearchDAO.insert(userSearch);
		} catch (Exception e) {
			logger.severe("Error while saving the user search.\n" 
					+ e.getStackTrace());
		}
	}

	@Override
	public void updateUserSearch(UserSearch userSearch) {
		logger.info("Updating user search...");
		try {
			userSearchDAO.update(userSearch);
		} catch (Exception e) {
			logger.severe("Error while updating the user search.\n" 
					+ e.getStackTrace());
		}
	}

	@Override
	public void deleteUserSearch(UserSearch userSearch) {
		logger.info("Deleting user search...");
		try {
			userSearchDAO.delete(userSearch);
		} catch (Exception e) {
			logger.severe("Error while deleting the user search.\n" 
					+ e.getStackTrace());
		}
	}

	@Override
	public UserSearch selectUserSearch(Long userSearchId) {
		logger.info("Getting user search: " + userSearchId + "...");
		UserSearch userSearch = null;
		try {
			userSearch = userSearchDAO.select(userSearchId);
		} catch (Exception e) {
			logger.severe("Error while selecting user search.\n" 
					+ e.getStackTrace());
		}
		return userSearch;
	}

	@Override
	public List<UserSearch> getSearchByUser(Map<String, Object> params) {
		logger.info("Getting user searchs by user...");
		return userSearchDAO.getUserSearchByUser(params);
	}

}
