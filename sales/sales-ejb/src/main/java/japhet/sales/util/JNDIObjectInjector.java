package japhet.sales.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import japhet.sales.except.UtilClassException;

public class JNDIObjectInjector<T> {

	private Logger logger = Logger.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	public T obtainJNDIObject(String binding) throws UtilClassException {
		T jndiObject = null;
		final String jndiNaming = "";
		final String infoMsg = String.format("Looking for the JNDI object: %s...", binding);
		final Hashtable<String, String> jndiProperties = new Hashtable<>();
		try {
			logger.info(infoMsg);
			jndiProperties.put(Context.URL_PKG_PREFIXES, jndiNaming);
			final Context context = (Context) new InitialContext(jndiProperties);
			final Object objectObtained = context.lookup(binding);
			jndiObject = (T) objectObtained;
		} catch (NamingException e) {
			final String fatalMsg = String.format("Error at obtaining the JNDI Object: %s.", 
					binding);
			logger.fatal(fatalMsg, e);
			throw new UtilClassException(fatalMsg, e);
		}
		return jndiObject;
	}
	
}
