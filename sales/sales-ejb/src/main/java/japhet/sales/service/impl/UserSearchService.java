package japhet.sales.service.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserSearchDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.UserSearch;
import japhet.sales.service.IUserSearchService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class UserSearchService implements IUserSearchService {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -6273934734754835975L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserSearchDAO userSearchDAO;

	@Override
	public void insertUserSearch(UserSearch userSearch)   
			throws BusinessServiceException {
		logger.info("Saving user search...");
		try {
			userSearchDAO.insert(userSearch);
		} catch (Exception e) {
			final String errorMsg = "Error while saving the user search.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void updateUserSearch(UserSearch userSearch)   
			throws BusinessServiceException {
		logger.info("Updating user search...");
		try {
			userSearchDAO.update(userSearch);
		} catch (Exception e) {
			final String errorMsg = "Error while updating the user search.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void deleteUserSearch(UserSearch userSearch)   
			throws BusinessServiceException {
		logger.info("Deleting user search...");
		try {
			userSearchDAO.delete(userSearch);
		} catch (Exception e) {
			final String errorMsg = "Error while deleting the user search.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public UserSearch selectUserSearch(Long userSearchId)   
			throws BusinessServiceException {
		logger.info("Getting user search: " + userSearchId + "...");
		UserSearch userSearch = null;
		try {
			userSearch = userSearchDAO.select(userSearchId);
		} catch (Exception e) {
			final String errorMsg = "Error while selecting user search.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userSearch;
	}

	@Override
	public List<UserSearch> getSearchByUser(Map<String, Object> params)   
			throws BusinessServiceException {
		logger.info("Getting user searchs by user...");
		List<UserSearch> userSearchs = null;
		try {
			userSearchs = userSearchDAO.getUserSearchByUser(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting searchs by user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userSearchs;
	}

}
