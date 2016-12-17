package japhet.sales.controller.home;

import static japhet.sales.catalogs.CompanyTypes.GOODS;
import static japhet.sales.catalogs.CompanyTypes.SERVICES;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.Product;
import japhet.sales.service.ICompanyService;
import japhet.sales.service.IProductService;

@ManagedBean
@ViewScoped
public class HomeMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 999398447133843098L;
	
	@Inject
	private Logger logger;
	
	@EJB
	private IProductService productService;
	
	@EJB
	private ICompanyService companyService;
	
	//View properties
	private List<Product> availableProducts;
	private List<Company> avalaibleServicesCompanies;
	private List<Company> avalaibleGoodsCompanies;
	
	@PostConstruct
	private void init(){
		try {
			logger.info("Initializing HomeMB...");
			//Initialize products
			setAvailableProducts(productService.getAllAvailableProducts());
			//Initialize companies
			setAvalaibleGoodsCompanies(getAllAvailableCompaniesOfType(GOODS.getCompanyTypeId()));
			setAvalaibleServicesCompanies(getAllAvailableCompaniesOfType(SERVICES.getCompanyTypeId()));
		} catch (Exception e) {
			logger.error("Error at initializing the HomeMB.", e);
		}
	}
	
	private List<Company> getAllAvailableCompaniesOfType(Short companyTypeId) 
			throws BusinessServiceException {
		return companyService.getAllAvailableCompaniesOfType(companyTypeId);
	}

	public List<Product> getAvailableProducts() {
		return availableProducts;
	}

	public void setAvailableProducts(List<Product> availableProducts) {
		this.availableProducts = availableProducts;
	}

	public List<Company> getAvalaibleServicesCompanies() {
		return avalaibleServicesCompanies;
	}

	public void setAvalaibleServicesCompanies(List<Company> avalaibleServicesCompanies) {
		this.avalaibleServicesCompanies = avalaibleServicesCompanies;
	}

	public List<Company> getAvalaibleGoodsCompanies() {
		return avalaibleGoodsCompanies;
	}

	public void setAvalaibleGoodsCompanies(List<Company> avalaibleGoodsCompanies) {
		this.avalaibleGoodsCompanies = avalaibleGoodsCompanies;
	}
}
