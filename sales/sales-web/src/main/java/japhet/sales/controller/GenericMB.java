package japhet.sales.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import japhet.sales.util.Navigator;

public abstract class GenericMB extends GenericFacesMessager
	implements Serializable, Navigator {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -5589709030464138421L;
	
	@Inject
	protected Logger logger;
	
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
}
