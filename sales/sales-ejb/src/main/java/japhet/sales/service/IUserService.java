package japhet.sales.service;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface IUserService extends Serializable {

	public boolean doesUserExists(String username, String passw);
	
}
