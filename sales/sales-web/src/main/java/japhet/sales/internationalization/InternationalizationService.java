package japhet.sales.internationalization;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Languages;
import japhet.sales.util.PropertiesReader;

@Singleton
@Startup
public class InternationalizationService 
	implements IInternationalizationService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 7580181958661230008L;

	//Shared element which contains all the languages
	private static Map<String, Languages> locales;
	
	//Session elements
	@Inject
	private Logger logger;
	
	private Map<String, PropertiesReader> pReaders;
	
	//Initialize languages
	static {
		locales = new HashMap<>();
		for(Languages language : Languages.values()){
			locales.put(language.getSuffix(), language);
		}
	}
		
	@Override
	@PostConstruct
	public void init() {
		try {
			logger.info("Initializing InternationalizationService...");
			//Initialize i18n maps
			pReaders = new HashMap<>();
			for(Languages language : Languages.values()){
				pReaders.put(language.getSuffix(), 
						new PropertiesReader(language.getI18NMessagesFile()));
			}
		} catch(Exception e) {
			logger.error("Error initializing InternationalizationService.", e);
		}
	}

	@Override
	public String getI18NMessage(String language, String key) {
		PropertiesReader pReader = null;
		String value = "";
		try {
			pReader = pReaders.get(language);
			value = pReader.getValueFromKey(key);
			if("".equals(value)) {
			value = key;
			}
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("Error while trying to obtain the following message: %s %s", language, key);
			logger.fatal(ERROR_MSG, e);
		}
		return value;
	}
}
