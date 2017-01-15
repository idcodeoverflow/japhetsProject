package japhet.sales.authorities;

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
	
}
