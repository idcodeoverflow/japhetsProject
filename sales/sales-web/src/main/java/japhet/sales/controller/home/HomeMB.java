package japhet.sales.controller.home;

import static japhet.sales.data.QueryParameters.SEARCHED_WORDS;
import static japhet.sales.util.StringUtils.wildcardParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.internationalization.IInternationalizationService;
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
	
	//EJB's
	@EJB
	private IProductService productService;
	
	@EJB
	private ICompanyService companyService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	//View properties
	private List<Product> availableProducts;
	private String searchedWords;
	
	@PostConstruct
	private void init(){
		try {
			logger.info("Initializing HomeMB...");
			//Initialize products
			setAvailableProducts(productService.getAllAvailableProducts());
		} catch (Exception e) {
			logger.error("Error at initializing the HomeMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	public void searchProducts(){
		final String ERROR_MSG = 
				String.format("Error while searching products which matches: %s...", searchedWords);
		try {
			logger.info(String.format("Searching products which matches: %s...", searchedWords));
			String wildcardedSearch = wildcardParameter(searchedWords);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(SEARCHED_WORDS, wildcardedSearch);
			setAvailableProducts(productService.getSearchedProducts(parameters));
		} catch (BusinessServiceException e) {
			logger.error(ERROR_MSG, e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		} catch (Exception e) {
			logger.error(ERROR_MSG, e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
	}
	
	public List<Product> getAvailableProducts() {
		return availableProducts;
	}

	public void setAvailableProducts(List<Product> availableProducts) {
		this.availableProducts = availableProducts;
	}
	
	public String getSearchedWords() {
		return searchedWords;
	}

	public void setSearchedWords(String searchedWords) {
		this.searchedWords = searchedWords;
	}
}
