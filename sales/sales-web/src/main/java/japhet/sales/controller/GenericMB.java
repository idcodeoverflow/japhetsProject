package japhet.sales.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import japhet.sales.model.impl.User;
import japhet.sales.util.Navigator;

public abstract class GenericMB extends GenericFacesMessager
	implements Serializable, Navigator {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -5589709030464138421L;
	
	protected String CURRENT_LOCALE = "";
	
	@Inject
	protected Logger logger;
	
	@Override
	protected FacesContext getCurrentFacesInstance(){
		return FacesContext.getCurrentInstance();
	}
	
	protected ExternalContext getExternalContext(){
		return getCurrentFacesInstance().getExternalContext();
	}
	
	protected String getRequestParam(String name) {
		return getCurrentFacesInstance().getExternalContext().
				getRequestParameterMap().get(name);
	}
	
	protected void redirect(String url){
		try {
			getCurrentFacesInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.error("Error while redirecting to: " + url, e);
			showErrorMessage("Error while redirecting to: " + url, "");
		}
	}
	
	protected HttpServletResponse getResponse(){
		HttpServletResponse response = (HttpServletResponse) 
				FacesContext.getCurrentInstance().getExternalContext().getResponse();
		return response;
	}
	
	protected HttpServletRequest getRequest(){
		HttpServletRequest request = (HttpServletRequest) 
				FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}
	
	protected User getLoggedUser(){
		User user = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if(authentication != null){
				Object object = authentication.getPrincipal();
				if(object instanceof User){
					user = (User) object;
				}
			}
		}
		return user;
	}
}
