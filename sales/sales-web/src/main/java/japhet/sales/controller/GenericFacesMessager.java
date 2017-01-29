package japhet.sales.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class GenericFacesMessager {
	
	//Error messages
	private final String GENERAL_ERROR = "userMessages.error.error";
	private final String STARTUP_MB_ERROR = "userMessages.error.startUpError";
	private final String TRY_AGAIN_ERROR = "userMessages.error.tryAgain";
	private final String IMAGE_UPLOAD_ERROR = "userMessages.error.image";
	private final String INVALID_PASSWORD_ERROR = "userMessages.error.invalidPassword";
	private final String SIGN_UP_ERROR = "userMessages.error.signupError";
	private final String PICK_STATE_ERROR = "userMessages.error.stateError";
	private final String SAVE_CONTENT_ERROR = "userMessages.error.saveContentError";
	private final String DATE_RANGE_INVALID = "userMessages.error.dateInvalid";
	private final String REDIRECT_ERROR = "userMessages.error.redirect";
	//Warning messages
	private final String PENDING_CHOOSE_CATEGORIES = "userMessages.warning.noCategoriesStored";
	//Info messages
	private final String IMAGE_READY = "userMessages.images.ready";
	private final String CONTENT_READY = "userMessages.content.ready";
	private final String UNREGISTERED_REDIRECT = "userMessages.unregistered.redirect";
	private final String SAVED_CONTENT = "userMessages.preferencesSaved";
	private final String USER_REGISTERED = "userMessages.userRegistered";
	
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

	public String getGENERAL_ERROR() {
		return GENERAL_ERROR;
	}

	public String getSTARTUP_MB_ERROR() {
		return STARTUP_MB_ERROR;
	}

	public String getTRY_AGAIN_ERROR() {
		return TRY_AGAIN_ERROR;
	}

	public String getIMAGE_UPLOAD_ERROR() {
		return IMAGE_UPLOAD_ERROR;
	}

	public String getINVALID_PASSWORD_ERROR() {
		return INVALID_PASSWORD_ERROR;
	}

	public String getSIGN_UP_ERROR() {
		return SIGN_UP_ERROR;
	}

	public String getPICK_STATE_ERROR() {
		return PICK_STATE_ERROR;
	}

	public String getSAVE_CONTENT_ERROR() {
		return SAVE_CONTENT_ERROR;
	}

	public String getDATE_RANGE_INVALID() {
		return DATE_RANGE_INVALID;
	}
	
	public String getREDIRECT_ERROR() {
		return REDIRECT_ERROR;
	}
	
	public String getPENDING_CHOOSE_CATEGORIES() {
		return PENDING_CHOOSE_CATEGORIES;
	}

	public String getIMAGE_READY() {
		return IMAGE_READY;
	}

	public String getCONTENT_READY() {
		return CONTENT_READY;
	}
	
	public String getUNREGISTERED_REDIRECT() {
		return UNREGISTERED_REDIRECT;
	}
	
	public String getSAVED_CONTENT() {
		return SAVED_CONTENT;
	}
	
	public String getUSER_REGISTERED() {
		return USER_REGISTERED;
	}
}
