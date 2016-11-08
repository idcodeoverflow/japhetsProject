package japhet.sales.controller.login;

import java.io.IOException;

import javax.annotation.PostConstruct;
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

@ManagedBean
@ViewScoped
public class LoginMB extends GenericMB 
	implements AuthConstants {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2539935060263981778L;
	
	@PostConstruct
	private void init(){
		//If the current user is currently signed in redirect to home
		if(isSignedIn()){
			redirect(HOME_URL);
		}
		//Update messages for the user
		updateMessages();
	}

	@Inject
	private Logger logger;
	
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
	
	public boolean isSignedIn(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && !(auth instanceof AnonymousAuthenticationToken);
	}
	
	public void updateMessages() {
	    Exception ex = (Exception)getExternalContext().
	    		getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(ex != null) {
		    logger.error("Authentication Failed!", ex);
		    showErrorMessage("Intente de nuevo", ex.getMessage());
		}
	}
	
	public short getMIN_PASSWORD_LENGTH() {
		return MIN_PASSWORD_LENGTH;
	}
	
	public short getMAX_PASSWORD_LENGTH() {
		return MAX_PASSWORD_LENGTH;
	}
	
	public short getMAX_EMAIL_LENGTH(){
		return MAX_EMAIL_LENGTH;
	}
}
