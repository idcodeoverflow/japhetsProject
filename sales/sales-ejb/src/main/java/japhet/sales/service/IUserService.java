package japhet.sales.service;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.except.UnmatchedPasswordException;
import japhet.sales.model.impl.User;

@Local
public interface IUserService extends Serializable {

	public short MINIMUM_PASSWORD_LENGTH = 8;
	
	public boolean doesUserExists(String username, String passw) 
			throws BusinessServiceException;
	
	public User getUser(Long userId) 
			throws BusinessServiceException;
	
	public boolean updateUser(User user) 
			throws BusinessServiceException;
	
	public boolean deleteUser(User user) 
			throws BusinessServiceException;
	
	public boolean insertUser(User user) 
			throws BusinessServiceException;
	
	public void validatePasswords(String pass1, String pass2) 
			throws InvalidPasswordException;
	
	public void modifyUserCategories(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public void updateUserPassword(Map<String, Object> params) 
			throws BusinessServiceException, 
				InvalidPasswordException, 
				UnmatchedPasswordException;
	
	public User getUserByHashKey(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public User getUserByUsername(Map<String, Object> params) 
			throws BusinessServiceException;
}
