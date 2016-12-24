package japhet.sales.controller.registration;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;
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

	//EJB's
	@EJB
	private IRoleService roleService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	private List<Role> roles;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing RoleMB...");
			roles = roleService.getAvailableRoles();
		} catch (Exception e) {
			logger.fatal("Error while initializing RolesMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, STARTUP_MB_ERROR), "");
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
