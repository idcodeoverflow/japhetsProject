package japhet.sales.service;

import java.io.Serializable;

import javax.ejb.Local;

import japhet.sales.model.impl.User;

@Local
public interface IUserService extends Serializable {

	public boolean doesUserExists(String username, String passw);
	
	public User getUser(Long userId);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(User user);
	
	public boolean insertUser(User user);
	
}
