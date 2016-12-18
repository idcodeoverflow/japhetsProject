package japhet.sales.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
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
	
	@PostConstruct
	private void init(){
		try {
			logger.info("Initializing HomeMB...");
			//Initialize products
			setAvailableProducts(productService.getAllAvailableProducts());
		} catch (Exception e) {
			logger.error("Error at initializing the HomeMB.", e);
		}
	}
	
	public List<Product> getAvailableProducts() {
		return availableProducts;
	}

	public void setAvailableProducts(List<Product> availableProducts) {
		this.availableProducts = availableProducts;
	}
}
