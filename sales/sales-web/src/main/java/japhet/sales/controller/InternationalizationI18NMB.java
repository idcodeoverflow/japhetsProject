package japhet.sales.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Languages;
import japhet.sales.util.PropertiesReader;

@ManagedBean
@SessionScoped
public class InternationalizationI18NMB 
	extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2659666980772461144L;
	
	//Shared element which contains all the languages
	private static Map<String, Languages> locales;
	
	//Initialize languages
	static {
		locales = new HashMap<>();
		for(Languages language : Languages.values()){
			locales.put(language.getSuffix(), language);
		}
	}
	
	//Session elements
	@Inject
	private Logger logger;
	
	private String suffix;
	private Languages locale;
	private PropertiesReader pReader;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing InternationalizationI18NMB...");
			//Get current locale
			suffix = "";
			locale = locales.get(suffix);
			logger.info(String.format("Locale selected: %s", locale.getI18NMessagesFile()));
			pReader = new PropertiesReader(locale.getI18NMessagesFile());
		} catch(Exception e) {
			logger.error("Error initializing InternationalizationI18NMB.", e);
			showErrorMessage("Error al iniciar internacionalizacion.", "");
		}
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Languages getLocale() {
		return locale;
	}

	public void setLocale(Languages locale) {
		this.locale = locale;
	}

	public String getI18NMessage(String key) {
		String value = pReader.getValueFromKey(key);
		if("".equals(value)) {
			value = key;
		}
		return value;
	}
}
