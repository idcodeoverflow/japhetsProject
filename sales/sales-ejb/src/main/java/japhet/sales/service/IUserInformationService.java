package japhet.sales.service;

import java.io.Serializable;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.UserInformation;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@Local
public interface IUserInformationService extends Serializable {

	/**
	 * Obtains the user information stored 
	 * into the DB for a certain User.
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public UserInformation getUserInformation(Long userId) 
			throws BusinessServiceException;
	
	/**
	 * Updates the user information stored 
	 * into the DB for a certain User.
	 * @param userInformation
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean updateUserInformation(UserInformation userInformation) 
			throws BusinessServiceException;
	
	/**
	 * Deletes the user information stored 
	 * into the DB for a certain User.
	 * @param userInformation
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean deleteUserInformation(UserInformation userInformation) 
			throws BusinessServiceException;
	
	/**
	 * Insert the user information specified 
	 * into the DB for a certain User.
	 * @param userInformation
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean insertUserInformation(UserInformation userInformation) 
			throws BusinessServiceException;
	
	/**
	 * Updates the user information specified of if the 
	 * entry doesn't exist insert the user information 
	 * specified for a certain User.
	 * @param userInformation
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean updateOrInsertUserInformation(UserInformation userInformation) 
			throws BusinessServiceException;
}
