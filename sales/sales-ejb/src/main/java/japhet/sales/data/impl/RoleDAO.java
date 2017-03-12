package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.Role;

import org.apache.log4j.Logger;

@Stateless
public class RoleDAO extends GenericDAO<Role, Short> {

	@Inject
	private Logger logger;
	
	public RoleDAO() {
		super(Role.class, Short.class);
	}
	
	public List<Role> getAllRoles() 
			throws DataLayerException {
		List<Role> roles = null;
		try {
			logger.info("Obtaining while getting all the roles from the DB...");
			roles = executeQuery(QueryNames.GET_ALL_ROLES, null);
		} catch (Exception e) {
			final String errorMsg = "Error while getting all the roles from the DB.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return roles;
	}
	
	public List<Role> getAllAvailableRoles() 
			throws DataLayerException {
		List<Role> roles = null;
		try {
			logger.info("Obtaining all the available roles from the DB...");
			roles = executeQuery(QueryNames.GET_ALL_AVAILABLE_ROLES, null);
		} catch (Exception e) {
			final String errorMsg = "Error while getting all the available roles from the DB.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return roles;
	}
}
