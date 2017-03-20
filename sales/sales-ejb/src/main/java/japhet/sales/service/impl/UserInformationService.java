package japhet.sales.service.impl;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.impl.UserInformationDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.UserInformation;
import japhet.sales.service.IUserInformationService;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@LocalBean
@Stateless
public class UserInformationService implements IUserInformationService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -2071155971343410964L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserInformationDAO userInformationDAO;
	
	/* (non-Javadoc)
	 * @see japhet.sales.service.IUserInformationService#getUserInformation(java.lang.Long)
	 */
	@Override
	public UserInformation getUserInformation(Long userId) 
			throws BusinessServiceException {
		final long USR_ID = ((userId != null) ? userId : -1L);
		final String INFO_MSG = String
				.format("Getting UserInformation for the User: %d...", USR_ID);
		UserInformation userInformation = null;
		try {
			logger.info(INFO_MSG);
			userInformation = userInformationDAO.select(userId);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has ocurred while getting the UserInformation: %d.", USR_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return userInformation;
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IUserInformationService#updateUserInformation(japhet.sales.model.impl.UserInformation)
	 */
	@Override
	public boolean updateUserInformation(UserInformation userInformation) 
			throws BusinessServiceException {
		final long USR_ID = ((userInformation != null 
				&& userInformation.getUserId() != null) ? userInformation.getUserId() : -1L);
		final String INFO_MSG = String
				.format("Updating UserInformation for the User: %d...", USR_ID);
		try {
			logger.info(INFO_MSG);
			userInformationDAO.update(userInformation);
			return true;
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while updating the UserInformation for the User: %d...", USR_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IUserInformationService#deleteUserInformation(japhet.sales.model.impl.UserInformation)
	 */
	@Override
	public boolean deleteUserInformation(UserInformation userInformation) 
			throws BusinessServiceException {
		final long USR_ID = ((userInformation != null 
				&& userInformation.getUserId() != null) ? userInformation.getUserId() : -1L);
		final String INFO_MSG = String
				.format("Deleting UserInformation from the User: %d...", USR_ID);
		try {
			logger.info(INFO_MSG);
			userInformationDAO.delete(userInformation);
			return true;
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while deleting UserInformation from the User: %d.", USR_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IUserInformationService#insertUserInformation(japhet.sales.model.impl.UserInformation)
	 */
	@Override
	public boolean insertUserInformation(UserInformation userInformation) 
			throws BusinessServiceException {
		final long USR_ID = ((userInformation != null 
				&& userInformation.getUserId() != null) ? userInformation.getUserId() : -1L);
		final String INFO_MSG = String
				.format("Inserting UserInformation from the User: %d...", USR_ID);
		try {
			logger.info(INFO_MSG);
			userInformationDAO.insert(userInformation);
			return true;
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while inserting the UserInformation from the User: %d...", USR_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IUserInformationService#updateOrInsertUserInformation(japhet.sales.model.impl.UserInformation)
	 */
	@Override
	public boolean updateOrInsertUserInformation(UserInformation userInformation) 
			throws BusinessServiceException {
		final long USR_ID = ((userInformation != null 
				&& userInformation.getUserId() != null) ? userInformation.getUserId() : -1L);
		final String INFO_MSG = String
				.format("Updating/Inserting UserInformation from the User: %d...", USR_ID);
		try {
			logger.info(INFO_MSG);
			if(USR_ID > 0) {
				UserInformation oldUsrInfo = userInformationDAO.select(USR_ID);
				if(oldUsrInfo != null) {
					userInformationDAO.update(userInformation);
					return true;
				}
				userInformationDAO.insert(userInformation);
			}
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while updating/inserting the UserInformation from the User: %d...", USR_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return false;
	}
}
