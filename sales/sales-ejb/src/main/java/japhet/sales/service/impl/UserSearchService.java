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
		try {
			logger.info("Saving user search...");
			userSearchDAO.insert(userSearch);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while saving the user search.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public void updateUserSearch(UserSearch userSearch)   
			throws BusinessServiceException {
		try {
			logger.info("Updating user search...");
			userSearchDAO.update(userSearch);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while updating the user search.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public void deleteUserSearch(UserSearch userSearch)   
			throws BusinessServiceException {
		try {
			logger.info("Deleting user search...");
			userSearchDAO.delete(userSearch);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while deleting the user search.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public UserSearch selectUserSearch(Long userSearchId)   
			throws BusinessServiceException {
		UserSearch userSearch = null;
		try {
			logger.info("Getting user search: " + userSearchId + "...");
			userSearch = userSearchDAO.select(userSearchId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while selecting user search.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return userSearch;
	}

	@Override
	public List<UserSearch> getSearchByUser(Map<String, Object> params)   
			throws BusinessServiceException {
		List<UserSearch> userSearchs = null;
		try {
			logger.info("Getting user searchs by user...");
			userSearchs = userSearchDAO.getUserSearchByUser(params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting searchs by user.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return userSearchs;
	}

}
