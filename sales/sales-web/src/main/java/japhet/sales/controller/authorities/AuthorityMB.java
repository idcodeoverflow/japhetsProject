package japhet.sales.controller.authorities;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Roles;
import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.Role;
import japhet.sales.model.impl.User;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@ManagedBean
@NoneScoped
public class AuthorityMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -9012980893144837111L;
	
	@Inject
	private Logger logger;

	/**
	 * Defines if the current user can upload products.
	 * @return
	 */
	public boolean isUploadProductsAllowed() {
		User user = getLoggedUser();
		if(user != null) {
			Role role = user.getRole();
			if(role != null) {
				return role.getRoleId() == Roles.COMPANY.getId();
			}
		}
		return false; 
	}
	
	/**
	 * Defines if the current user has the user role.
	 * @return
	 */
	public boolean isUserRole() {
		User user = getLoggedUser();
		if(user != null) {
			Role role = user.getRole();
			if(role != null) {
				return role.getRoleId() == Roles.USER.getId();
			}
		}
		return false; 
	}
	
	/**
	 * Defines if the current user has the company role.
	 * @return
	 */
	public boolean isCompanyRole() {
		User user = getLoggedUser();
		if(user != null) {
			Role role = user.getRole();
			if(role != null) {
				return role.getRoleId() == Roles.COMPANY.getId();
			}
		}
		return false; 
	}
	
	/**
	 * Defines if the current user has the administrator role.
	 * @return
	 */
	public boolean isAdministratorRole() {
		User user = getLoggedUser();
		if(user != null) {
			Role role = user.getRole();
			if(role != null) {
				return role.getRoleId() == Roles.ADMINISTRATOR.getId();
			}
		}
		return false; 
	}
	
	/**
	 * Allows or denies a user to register an administrator or a company.
	 * @return
	 */
	public boolean isMultiUserRoleRegistrationAllowed() {
		User user = getLoggedUser();
		if(user != null) {
			Role role = user.getRole();
			if(role != null) {
				return role.getRoleId() == Roles.ADMINISTRATOR.getId();
			}
		}
		return false;
	}
	
	/**
	 * Verifies if the current logged user is an administrator.
	 * @return
	 */
	public boolean isAdministratorLogged() {
		User user = getLoggedUser();
		if(user != null) {
			Role role = user.getRole();
			if(role != null) {
				return role.getRoleId() == Roles.ADMINISTRATOR.getId();
			}
		}
		return false;
	}
	
	/**
	 * @return True if the current logged User is 
	 * Company and has ProductOrder permissions.
	 */
	public boolean isProductOrderEnabled() {
		try {
			if(isCompanyRole()) {
				Company company = getLoggedCompany();
				return company != null && company.getOrdersAvailable();
			}
		} catch (Exception e) {
			logger.error("An error has ocurred while validating if the current Logged Company has ProductOrder permissions.");
			showGeneralExceptionMessage();
		}
		return false;
	}
}
