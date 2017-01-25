package japhet.sales.controller.authorities;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import japhet.sales.catalogs.Roles;
import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Role;
import japhet.sales.model.impl.User;

@ManagedBean
@NoneScoped
public class AuthorityMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -9012980893144837111L;

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
}
