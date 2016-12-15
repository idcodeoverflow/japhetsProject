package japhet.sales.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class GenericFacesMessager {

	protected FacesContext getCurrentFacesInstance(){
		return FacesContext.getCurrentInstance();
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
	
	protected boolean validCredential(String cred) {
		return cred != null && !"".equals(cred);
	}
}