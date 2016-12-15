package japhet.sales.controller.registration;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.CompanyType;
import japhet.sales.service.ICompanyTypeService;

@ManagedBean
@ApplicationScoped
public class CompanyTypeMB 
	extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -1286738783811640545L;
	
	@Inject
	private Logger logger;
	
	@EJB
	private ICompanyTypeService companyTypeService;
	
	private List<CompanyType> companyTypes;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing CompanyTypesMB...");
			this.companyTypes = companyTypeService.getCachedCompanyTypes();
		} catch (Exception e) {
			logger.error("Error while initializing CompanyTypesMB.", e);
			showErrorMessage("Ha ocurrido un error.", "");
		}
	}

	public List<CompanyType> getCompanyTypes() {
		return companyTypes;
	}
}
