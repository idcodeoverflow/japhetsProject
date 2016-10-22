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
		logger.info("Obtaining all available roles...");
		return roleDAO.getAllAvailableRoles();
	}

	@Override
	public List<Role> getAllRoles()   
			throws BusinessServiceException {
		logger.info("Obtaining all roles...");
		return roleDAO.getAllRoles();
	}
	
	public Role getRole(Short roleId)   
			throws BusinessServiceException {
		logger.info("Obtaining role " + roleId + " from the DB...");
		try {
			return roleDAO.select(roleId);
		} catch (Exception e) {
			logger.fatal("Error obtaining role " + roleId + " from the DB.", e);
		}
		return null;
	}
	
	public boolean updateRole(Role role)   
			throws BusinessServiceException {
		logger.info("Updating role into the DB...");
		try {
			roleDAO.update(role);
			return true;
		} catch (Exception e) {
			logger.fatal("Error updating role into the DB.", e);
		}
		return false;
	}
	
	public boolean deleteRole(Role role)   
			throws BusinessServiceException {
		logger.info("Deleting role into the DB...");
		try {
			roleDAO.delete(role);
			return true;
		} catch (Exception e) {
			logger.fatal("Error deleting role into the DB.", e);
		}
		return false;
	}
	
	public boolean insertRole(Role role)   
			throws BusinessServiceException {
		logger.info("Inserting role into the DB...");
		try {
			roleDAO.insert(role);
			return true;
		} catch (Exception e) {
			logger.fatal("Error inserting role into the DB.", e);
		}
		return false;
	}

	public List<Role> getAvailableRoles()   
			throws BusinessServiceException {
		return availableRoles;
	}

	public void setAvailableRoles(List<Role> availableRoles)   
			throws BusinessServiceException {
		this.availableRoles = availableRoles;
	}
}
