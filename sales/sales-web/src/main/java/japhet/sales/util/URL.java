package japhet.sales.util;

public enum URL {
	//Server Pages
	HOST_NAME("server.hostName"),
	//Application Pages
	HOME("home"),
	LOGIN("login"),
	UPLOAD("upload"),
	BANKS_N_DEPOSITS("banksNDeposits"),
	CAROUSELS("carousels"),
	OFFERS("offers"),
	RECOVER_PASSWORD("recoverPassword"),
	SIGN_UP("signUp"),
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
