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
	
	public String getPathsMediaResources(){
		return PATHS_MEDIA_RESOURCES;
	}
	
	public String getPathsLibraries(){
		return PATHS_LIBRARIES;
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
	
	public String getCategories(){
		return CATEGORIES;
	}
	
	public String getHomeVideoURL(){
		return HOME_VIDEO_URL;
	}
	
	public String getBanksNDepositsURL(){
		return BANKS_N_DEPOSITS;
	}
	
	public String getCarouselsURL(){
		return CAROUSELS;
	}
	
	public String getOffersURL(){
		return OFFERS;
	}
	
	public String getModalURL(){
		return MODAL;
	}
	
	public String getInbursaURL(){
		return INBURSA;
	}
	
	public String getProfuturoURL(){
		return PROFUTURO;
	}
	
	public String getPensionisssteURL(){
		return PENSIONISSSTE;
	}
	
	public String getCoppelURL(){
		return COPPEL;
	}
	
	public String getAztecaURL(){
		return AZTECA;
	}
	
	public String getBanorteURL(){
		return BANORTE;
	}

	public String getMetlifeURL(){
		return METLIFE;
	}

	public String getCitibanamexURL(){
		return CITIBANAMEX;
	}
	
	public String getInvercapURL(){
		return INBURSA;
	}
	
	public String getSuraURL(){
		return SURA;
	}
	
	public String getPrincipalURL(){
		return PRINCIPAL;
	}
	
	public String getAccountManager(){
		return ACCOUNT_MANAGER;
	}
	
	public String getUserAccountManager(){
		return USER_ACCOUNT_MANAGER;
	}
	
	public static String removeHostNameFromURL(String url){
		return url.replace(HOST_NAME, "");
	}
}
