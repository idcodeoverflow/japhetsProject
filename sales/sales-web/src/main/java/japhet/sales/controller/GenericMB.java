package japhet.sales.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.util.Navigator;

public abstract class GenericMB implements Serializable, Navigator {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -5589709030464138421L;
	
	@Inject
	protected Logger logger;
	
	protected FacesContext getCurrentFacesInstance(){
		return FacesContext.getCurrentInstance();
	}
	
	protected String getRequestParam(String name) {
		return getCurrentFacesInstance().getExternalContext().
				getRequestParameterMap().get(name);
	}
	
	protected FacesMessage createInfoMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}

	protected FacesMessage createWarnMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_WARN, message, detail);
	}
	
	protected FacesMessage createErrorMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}
	
	protected FacesMessage createFatalMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}
	
	protected void showInfoMessage(String message, String detail){
		getCurrentFacesInstance().
			addMessage(null, createInfoMessage(message, detail));
	}

	protected void showWarnMessage(String message, String detail){
		getCurrentFacesInstance().
			addMessage(null, createWarnMessage(message, detail));
	}
	
	protected void showErrorMessage(String message, String detail){
		getCurrentFacesInstance().
			addMessage(null, createErrorMessage(message, detail));
	}
	
	protected void showFatalMessage(String message, String detail){
		getCurrentFacesInstance().
			addMessage(null, createFatalMessage(message, detail));
	}
	
	protected void redirect(String url){
		try {
			getCurrentFacesInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.error("Error while redirecting to: " + url, e);
			showErrorMessage("Error while redirecting to: " + url, "");
		}
	}
}
