package japhet.sales.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.model.impl.User;

import org.apache.log4j.Logger;

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
			logger.info("Validating credentials...");
			List<User> users = new ArrayList<>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", user.getUsername());
			params.put("passw", user.getPassw());
			executeQuery(QueryNames.EXISTS_USER, params);
			userExists = users.size() > 0;
		} catch (Exception e) {
			logger.fatal("Exception occurred searching the user credentials into the DB.", e);
		}
		return userExists;
	}
}
