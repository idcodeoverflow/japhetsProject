package japhet.sales.catalogs;

public enum URL {
	//Server Pages
	HOST_NAME("server.hostName"),
	//Libraries' paths
	PATHS_MEDIA_RESOURCES("paths.media.resources"),
	PATHS_LIBRARIES("paths.libraries"),
	//Application Pages
	HOME("home"),
	LOGIN("login"),
	UPLOAD("upload"),
	BANKS_N_DEPOSITS("banksNDeposits"),
	AFORE_CHOOSING_BANNER("aforeChoosingBanner"),
	CAROUSELS("carousels"),
	OFFERS("offers"),
	MODAL("providerPrivacyPolicy"),
	RECOVER_PASSWORD("recoverPassword"),
	CHANGE_PASSWORD("changePassword"),
	SIGN_UP("signUp"),
	ACCOUNT_MANAGER("accountManager"),
	COMPANY_MANAGER("companyAccountManager"),
	USER_ACCOUNT_MANAGER("userAccountManager"),
	USER_INFORMATION("userInformation"),
	CATEGORIES("categories"),
	CATEGORIES_REGISTRATION_URL("categoriesRegistration"),
	HOME_VIDEO_URL("homeVideoUrl"),
	//Banks
	INBURSA("banks.inbursa"),
	PROFUTURO("banks.profuturo"),
	PENSIONISSSTE("banks.pensionissste"),
	COPPEL("banks.coppel"),
	AZTECA("banks.azteca"),
	BANORTE("banks.banorte"),
	METLIFE("banks.metlife"),
	CITIBANAMEX("banks.citibanamex"),
	INVERCAP("banks.invercap"),
	SURA("banks.sura"),
	PRINCIPAL("banks.principal"),
	//Security
	SPRING_LOGIN("security.login"),
	SPRING_LOGOUT("security.logout")
	;
	

	private String name;
	
	URL(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
