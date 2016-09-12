package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.User;

@Stateless
public class UserDAO extends GenericDAO<User, Long> {

	public UserDAO(Class<User> type, Class<Long> key) {
		super(type, key);
	}	
	
}
