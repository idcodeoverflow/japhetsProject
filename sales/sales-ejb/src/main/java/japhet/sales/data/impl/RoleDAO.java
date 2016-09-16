package japhet.sales.data.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.model.impl.Role;

@Stateless
public class RoleDAO extends GenericDAO<Role, Short> {

	@Inject
	private Logger logger;
	
	public RoleDAO() {
		super(Role.class, Short.class);
	}
	
	public List<Role> getAllRoles(){
		List<Role> roles = null;
		logger.info("Error while getting all the roles from the DB...");
		try {
			roles = executeQuery(QueryNames.GET_ALL_ROLES, null);
		} catch (Exception e) {
			logger.severe("Error while getting all the roles from the DB." 
					+ e.getStackTrace());
		}
		return roles;
	}
	
	public List<Role> getAllAvailableRoles(){
		List<Role> roles = null;
		logger.info("Error while getting all the available roles from the DB...");
		try {
			roles = executeQuery(QueryNames.GET_ALL_AVAILABLE_ROLES, null);
		} catch (Exception e) {
			logger.severe("Error while getting all the available roles from the DB." 
					+ e.getStackTrace());
		}
		return roles;
	}

}
