package japhet.sales.controller.home;

import static japhet.sales.catalogs.CompanyTypes.*;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.CompanyTypes;
import japhet.sales.controller.GenericMB;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Company;
import japhet.sales.service.ICompanyService;

@ManagedBean
@SessionScoped
public class HomeCarouselMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 6378174787250183188L;

	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private ICompanyService companyService;
	
	//View attributes
	private List<Company> goodsCompanies;
	private List<Company> servicesCompanies;
	
	//Logic attributes
	private int goodsCompaniesIndex;
	private int servicesCompaniesIndex;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing HomeCarouselMB...");
			//Indexes initialing
			goodsCompaniesIndex = 0;
			servicesCompaniesIndex = 0;
			//Services calls
			this.goodsCompanies = getAllAvailableCompaniesOfType(GOODS);
			this.servicesCompanies = getAllAvailableCompaniesOfType(SERVICES);
		} catch (BusinessServiceException e) {
			logger.error("Error while initializing HomeCarouselMB.", e);
			showErrorMessage("Se ha generado un error al iniciar la pagina.", "");
		} catch (Exception e) {
			logger.error("Error while initializing HomeCarouselMB.", e);
			showErrorMessage("Se ha generado un error al iniciar la pagina.", "");
		}
	}
	
	public int getGoodsCompaniesIndex() {
		return goodsCompaniesIndex++;
	}

	public int getServicesCompaniesIndex() {
		return servicesCompaniesIndex++;
	}

	public String getGoodsCompanyElementStyleClass(Company company) {
		final String EMPTY = "";
		final String ACTIVE = "active";
		return ((isActiveGoodsCompanyElement(company)) ? ACTIVE : EMPTY);
	}
	
	public String getServicesCompanyElementStyleClass(Company company) {
		final String EMPTY = "";
		final String ACTIVE = "active";
		return ((isActiveServicesCompanyElement(company)) ? ACTIVE : EMPTY);
	}
	
	private List<Company> getAllAvailableCompaniesOfType(CompanyTypes companyType) 
			throws BusinessServiceException {
		return companyService.getAllAvailableCompaniesOfType(companyType.getCompanyTypeId());
	}

	public List<Company> getGoodsCompanies() {
		return goodsCompanies;
	}

	public void setGoodsCompanies(List<Company> goodsCompanies) {
		this.goodsCompanies = goodsCompanies;
	}

	public List<Company> getServicesCompanies() {
		return servicesCompanies;
	}

	public void setServicesCompanies(List<Company> servicesCompanies) {
		this.servicesCompanies = servicesCompanies;
	}
	
	private boolean isActiveGoodsCompanyElement(Company company) {
		return isActiveCompanyElement(goodsCompanies, company);
	}
	
	private boolean isActiveServicesCompanyElement(Company company) {
		return isActiveCompanyElement(servicesCompanies, company);
	}
	
	private boolean isActiveCompanyElement(List<Company> companies, Company company) {
		//Validate parameters
		if(companies == null || 
				company == null || 
				companies.size() <= 0) {
			return false;
		}
		return companies.get(0).getCompanyId() == company.getCompanyId();
	}
}
