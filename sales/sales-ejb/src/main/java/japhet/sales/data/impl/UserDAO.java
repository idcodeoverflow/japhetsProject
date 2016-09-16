package japhet.sales.data.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.NamedQueries;
import japhet.sales.model.impl.User;

@Stateless
public class UserDAO extends GenericDAO<User, Long> {

	@Inject
	private Logger logger;
	
	public UserDAO() {
		super(User.class, Long.class);
	}	
	
	public boolean doesUserExists(User user){
		boolean userExists = false;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			Query query = em.createNamedQuery(NamedQueries.EXISTS_USER, User.class);
			params.put("username", user.getUsername());
			params.put("passw", user.getPassw());
			populateNamedQueryParams(query, params);
		} catch (Exception e) {
			logger.severe("Exception occurred searching the user credentials into the DB." + 
					e.getStackTrace());
		}
		return userExists;
	}
	
}
