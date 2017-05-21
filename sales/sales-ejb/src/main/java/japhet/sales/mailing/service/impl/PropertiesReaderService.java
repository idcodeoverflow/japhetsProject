package japhet.sales.mailing.service.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.mailing.service.IPropertiesReaderService;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@Singleton
@Startup
public class PropertiesReaderService implements IPropertiesReaderService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5768708964462890452L;

	@Inject
	private Logger logger;
	
	private Properties properties;
	
	@PostConstruct
	private void init() {
		final String INFO_MSG = "Initializing PropertiesReaderService...";
		try {
			logger.info(INFO_MSG);
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);
			properties = new Properties();
			properties.load(inputStream);
		} catch (Exception e) {
			final String ERROR_MSG = "An error has occurred while initializing the PropertiesReaderService.";
			logger.fatal(ERROR_MSG, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see japhet.sales.mailing.service.IPropertiesReader#getProperties(java.lang.String)
	 */
	@Override
	public String getProperty(String key) throws Exception {
		String propValue = null;
		final String INFO_MSG = String.format("Obtaining MailingProperty: %s...", key);
		logger.info(INFO_MSG);
		propValue = properties.getProperty(key);
		return propValue;
	}

}
