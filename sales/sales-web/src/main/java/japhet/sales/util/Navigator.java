package japhet.sales.util;

public interface Navigator {
	
	PropertiesReader pReader = new PropertiesReader("urls.properties");
	
	String HOME_URL = pReader.getValueFromKey(URL.HOME.getName());
	String BANKS_N_DEPOSITS = pReader.getValueFromKey(URL.BANKS_N_DEPOSITS.getName());
	String CAROUSELS = pReader.getValueFromKey(URL.CAROUSELS.getName());;
	String OFFERS = pReader.getValueFromKey(URL.OFFERS.getName());;
	String REGISTRATION_URL = pReader.getValueFromKey(URL.SIGN_UP.getName());
	String UPLOAD_CONTENT_URL = pReader.getValueFromKey(URL.UPLOAD.getName());
	String RECOVER_PASSWOR_URL = pReader.getValueFromKey(URL.RECOVER_PASSWORD.getName());
	String SIGN_IN_URL = pReader.getValueFromKey(URL.LOGIN.getName());
	String HOME_VIDEO_URL = pReader.getValueFromKey(URL.HOME_VIDEO_URL.getName());
	/***** BANKS *****/
	String INBURSA = pReader.getValueFromKey(URL.INBURSA.getName());
	String PROFUTURO = pReader.getValueFromKey(URL.PROFUTURO.getName());
	String PENSIONISSSTE = pReader.getValueFromKey(URL.PENSIONISSSTE.getName());
	String COPPEL = pReader.getValueFromKey(URL.COPPEL.getName());
	String AZTECA = pReader.getValueFromKey(URL.AZTECA.getName());
	String BANORTE = pReader.getValueFromKey(URL.BANORTE.getName());
	String METLIFE = pReader.getValueFromKey(URL.METLIFE.getName());
	String CITIBANAMEX = pReader.getValueFromKey(URL.CITIBANAMEX.getName());
	String INVERCAP = pReader.getValueFromKey(URL.INVERCAP.getName());
	String SURA = pReader.getValueFromKey(URL.SURA.getName());
	String PRINCIPAL = pReader.getValueFromKey(URL.PRINCIPAL.getName());
}
