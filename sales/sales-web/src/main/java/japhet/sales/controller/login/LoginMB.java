package japhet.sales.controller.login;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import japhet.sales.controller.AuthConstants;
import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;

@ManagedBean
@ViewScoped
public class LoginMB extends GenericMB 
	implements AuthConstants {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2539935060263981778L;

	@Inject
	private Logger logger;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	@PostConstruct
	private void init() {
		try {
			logger.error("Initializing LoginMB...");
			//Update messages for the user
			updateMessages();
		} catch (Exception e) {
			logger.error("An error has ocurred while initializing LoginMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	public String doLogin() throws IOException, ServletException {
		logger.info("Authenticating user...");
        ExternalContext context = getExternalContext();
        RequestDispatcher dispatcher = 
        		((ServletRequest) context.getRequest()).
        			getRequestDispatcher(SPRING_LOGIN);
        dispatcher.forward((ServletRequest) context.getRequest(), 
        		(ServletResponse) context.getResponse());
        getCurrentFacesInstance().responseComplete();
        return null;
    }
	
	public String doLogout() throws IOException, ServletException {
		if (isSignedIn()){   
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        logger.info("User: " + auth.getName() + " signed out.");
			new SecurityContextLogoutHandler().logout(getRequest(), getResponse(), auth);
	    }
	    return null;
    }
	
	public boolean isSignedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null 
				&& !(auth instanceof AnonymousAuthenticationToken);
	}
	
	private void updateMessages() {
	    Exception ex = (Exception)getExternalContext().
	    		getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
	    //If something went wrong
	    if(ex != null) {
	        logger.error("Authentication Failed!", ex);
	        showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getINVALID_PASSWORD_ERROR()), "");
	    }
	}
	
	public short getMIN_PASSWORD_LENGTH() {
		return MIN_PASSWORD_LENGTH;
	}
	
	public short getMAX_PASSWORD_LENGTH() {
		return MAX_PASSWORD_LENGTH;
	}
	
	public short getMAX_EMAIL_LENGTH() {
		return MAX_EMAIL_LENGTH;
	}
}
