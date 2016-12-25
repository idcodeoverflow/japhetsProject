package japhet.sales.internationalization;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface IInternationalizationService 
	extends Serializable {

	public void init();
	
	public String getI18NMessage(String language, String key);
	
}
