package japhet.sales.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.RoleDAO;
import japhet.sales.model.impl.Role;
import japhet.sales.service.IRoleService;

@LocalBean
@Stateless
public class RoleService implements IRoleService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -8292091081801377297L;

	@Inject
	private Logger logger;
	
	@EJB
	private RoleDAO roleDAO;
	
	@Override
	public List<Role> getAllAvailableRoles() {
		logger.info("Obtaining all available roles...");
		return roleDAO.getAllAvailableRoles();
	}

	@Override
	public List<Role> getAllRoles() {
		logger.info("Obtaining all roles...");
		return roleDAO.getAllRoles();
	}
	
	public Role getRole(Short roleId) {
		logger.info("Obtaining role " + roleId + " from the DB...");
		try {
			return roleDAO.select(roleId);
		} catch (Exception e) {
			logger.severe("Error obtaining role " + roleId + " from the DB.");
		}
		return null;
	}
	
	public boolean updateRole(Role role) {
		logger.info("Updating role into the DB...");
		try {
			roleDAO.update(role);
			return true;
		} catch (Exception e) {
			logger.severe("Error updating role into the DB. \n" + e.getStackTrace());
		}
		return false;
	}
	
	public boolean deleteRole(Role role) {
		logger.info("Deleting role into the DB...");
		try {
			roleDAO.delete(role);
			return true;
		} catch (Exception e) {
			logger.severe("Error deleting role into the DB. \n" + e.getStackTrace());
		}
		return false;
	}
	
	public boolean insertRole(Role role) {
		logger.info("Inserting role into the DB...");
		try {
			roleDAO.insert(role);
			return true;
		} catch (Exception e) {
			logger.severe("Error inserting role into the DB. \n" + e.getStackTrace());
		}
		return false;
	}

}
