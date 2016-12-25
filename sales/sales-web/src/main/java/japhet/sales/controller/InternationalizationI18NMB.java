package japhet.sales.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.internationalization.IInternationalizationService;

@ManagedBean
@SessionScoped
public class InternationalizationI18NMB 
	extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2659666980772461144L;
	
	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private IInternationalizationService internationalizationService;
		
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing InternationalizationI18NMB...");
			//Get current locale
			logger.info(String.format("Locale selected: %s", CURRENT_LOCALE));
		} catch(Exception e) {
			logger.error("Error initializing InternationalizationI18NMB.", e);
			showErrorMessage("Error al iniciar internacionalizacion.", "");
		}
	}

	public String getLocale() {
		return CURRENT_LOCALE;
	}

	public void setLocale(String CURRENT_LOCALE) {
		this.CURRENT_LOCALE = CURRENT_LOCALE;
	}

	public String getI18NMessage(String key) {
		String value = internationalizationService.getI18NMessage(CURRENT_LOCALE, key);
		if("".equals(value)) {
			value = key;
		}
		return value;
	}
}
