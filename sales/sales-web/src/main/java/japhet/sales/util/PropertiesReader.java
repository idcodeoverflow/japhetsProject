package japhet.sales.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.log4j.Logger;

public class PropertiesReader {
	
	@Inject
	private Logger logger = Logger.getLogger(getClass());
	
	private Properties prop;
	private InputStream input;
	
	public PropertiesReader(String propsFile) {
		try {
			prop = new Properties();
			logger.info("Initializing PropertiesReader: " + propsFile);
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream(propsFile);
			prop.load(input);
		} catch (IOException e) {
			logger.fatal("Error trying to initialize the PropertiesReader: " 
					+ propsFile, e);
		}
	}
	
	public String getValueFromKey(String key){
		String value = "";
		try {
			value = prop.getProperty(key);
		} catch (Exception e) {
			logger.fatal("Error while trying to obtain the value for the Key: " 
					+ key, e);
		}
		return value;
	}

}
