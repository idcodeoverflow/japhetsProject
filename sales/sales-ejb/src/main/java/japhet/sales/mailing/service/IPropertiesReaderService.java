package japhet.sales.mailing.service;

import java.io.Serializable;

import javax.ejb.Local;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@Local
public interface IPropertiesReaderService extends Serializable {

	public final String PROPERTIES_PATH = "META-INF/mailing/mailing.properties";
	
	public String getProperty(String key) throws Exception;
}
