package japhet.sales.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import japhet.sales.data.impl.RoleDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Role;
import japhet.sales.service.IRoleService;

import org.apache.log4j.Logger;

@Startup
@Singleton
public class RoleService implements IRoleService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -8292091081801377297L;

	@Inject
	private Logger logger;
	
	@EJB
	private RoleDAO roleDAO;
	
	private List<Role> availableRoles;
	
	@PostConstruct
	private void init(){
		try {
			logger.info("Initializing Role Service...");
			availableRoles = getAllAvailableRoles();
		} catch (BusinessServiceException e) {
			logger.fatal("Error initializing Role Service.", e);
		}
	}
	
	@Override
	public List<Role> getAllAvailableRoles()   
			throws BusinessServiceException {
		List<Role> roles = null;
		try {
			logger.info("Obtaining all available roles...");
			roles = roleDAO.getAllAvailableRoles();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all available roles.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return roles;
	}

	@Override
	public List<Role> getAllRoles()   
			throws BusinessServiceException {
		List<Role> roles = null;
		try {
			logger.info("Obtaining all roles...");
			roles = roleDAO.getAllRoles();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all roles.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return roles;
	}
	
	@Override
	public Role getRole(Short roleId)   
			throws BusinessServiceException {
		try {
			logger.info("Obtaining role " + roleId + " from the DB...");
			return roleDAO.select(roleId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining role " + roleId + " from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean updateRole(Role role)   
			throws BusinessServiceException {
		try {
			logger.info("Updating role into the DB...");
			roleDAO.update(role);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error updating role into the DB: " 
					+ stringifyRole(role);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean deleteRole(Role role)   
			throws BusinessServiceException {
		try {
			logger.info("Deleting role into the DB...");
			roleDAO.delete(role);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error deleting role into the DB: " 
					+ stringifyRole(role);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean insertRole(Role role)   
			throws BusinessServiceException {
		try {
			logger.info("Inserting role into the DB...");
			roleDAO.insert(role);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error inserting role into the DB: " 
					+ stringifyRole(role);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public List<Role> getAvailableRoles()   
			throws BusinessServiceException {
		List<Role> roles = null;
		try {
			logger.info("Getting all available Roles...");
			roles = availableRoles;
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting available roles.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return roles;
	}

	@Override
	public void setAvailableRoles(List<Role> availableRoles)   
			throws BusinessServiceException {
		this.availableRoles = availableRoles;
	}
	
	private Short stringifyRole(Role role) {
		return ((role != null && role.getRoleId() != null) 
				? role.getRoleId() : null);
	}
}
