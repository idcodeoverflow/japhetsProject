package japhet.sales.service;

import java.io.Serializable;

import javax.ejb.Local;

import japhet.sales.except.InvalidPasswordException;
import japhet.sales.model.impl.User;

@Local
public interface IUserService extends Serializable {

	public short MINIMUM_PASSWORD_LENGTH = 8;
	
	public boolean doesUserExists(String username, String passw);
	
	public User getUser(Long userId);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(User user);
	
	public boolean insertUser(User user);
	
	public void validatePasswords(String pass1, String pass2) throws InvalidPasswordException;
	
}
