package japhet.sales.controller.login;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.User;

@ManagedBean
@SessionScoped
public class UserDetailsMB extends GenericMB {

	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -1899644880367193472L;

	//Logger
	@Inject
	private Logger logger;
	
	//View properties
	private User user;
	
	//EJB
	@EJB
	private IInternationalizationService internationalizationService;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing UserDetailsMB...");
			user = new User();
			Object principal = SecurityContextHolder.
					getContext().getAuthentication().getPrincipal();
			user = (User) principal;
		} catch (Exception e) {
			logger.error("An error has ocurred initializing UserDetailsMB", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	/**
	 * Obtains the full name of the current logged user.
	 * @return user's fullname.
	 */
	public String getFullName() {
		StringBuilder fullname = new StringBuilder();
		if(user != null) {
			if(user.getName() != null) {
				fullname.append(user.getName());
			}
			if(user.getLastName() != null) {
				if(fullname.length() > 0) {
					fullname.append(" ");
				}
				fullname.append(user.getLastName());
			}
		}
		return fullname.toString();
	}
}
