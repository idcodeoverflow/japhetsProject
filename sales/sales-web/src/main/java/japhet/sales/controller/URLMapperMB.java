package japhet.sales.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@NoneScoped
@ManagedBean(name = "urlMapperMB")
public class URLMapperMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -4461006256147127447L;

	public String getHomeURL(){
		return HOME_URL;
	}
	
	public String getRegistrationURL(){
		return REGISTRATION_URL;
	}
	
	public String getUploadContentURL(){
		return UPLOAD_CONTENT_URL;
	}
	
	public String getRecoverPasswordURL(){
		return RECOVER_PASSWOR_URL;
	}
	
	public String getSignInURL(){
		return SIGN_IN_URL;
	}
	
	public String getHomeVideoURL(){
		return HOME_VIDEO_URL;
	}
}
