package japhet.sales.controller.registration;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Role;
import japhet.sales.service.IRoleService;

@ManagedBean
@ApplicationScoped
public class RoleMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 3074656295341736539L;
	
	@Inject
	private Logger logger;

	@EJB
	private IRoleService roleService;
	
	private List<Role> roles;
	
	@PostConstruct
	private void init() {
		logger.info("Inicializando roles desde la bd...");
		roles = roleService.getAvailableRoles();
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
