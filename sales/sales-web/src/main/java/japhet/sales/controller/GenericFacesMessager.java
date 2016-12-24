package japhet.sales.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class GenericFacesMessager {
	
	//Error messages
	public final String GENERAL_ERROR = "userMessages.general.error";
	public final String STARTUP_MB_ERROR = "userMessages.general.startUpError";
	public final String TRY_AGAIN_ERROR = "userMessages.genera.tryAgain";
	public final String IMAGE_UPLOAD_ERROR = "userMessages.error.image";
	public final String INVALID_PASSWORD_ERROR = "userMessages.error.invalidPassword";
	public final String SIGN_UP_ERROR = "userMessages.error.signupError";
	public final String PICK_STATE_ERROR = "userMessages.error.stateError";
	public final String SAVE_CONTENT_ERROR = "userMessages.error.saveContentError";
	public final String DATE_RANGE_INVALID = "userMessages.error.dateInvalid";
	//Info messages
	public final String IMAGE_READY = "userMessages.images.ready";
	public final String CONTENT_READY = "userMessages.content.ready";
	
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
