package japhet.sales.controller.home;

import static japhet.sales.util.StringUtils.wildcardParameter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Roles;
import japhet.sales.controller.GenericMB;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.Category;
import japhet.sales.model.impl.Product;
import japhet.sales.model.impl.User;
import japhet.sales.model.impl.UserInformation;
import japhet.sales.model.impl.UserProductHistorial;
import japhet.sales.model.impl.UserSearch;
import japhet.sales.service.ICompanyService;
import japhet.sales.service.IProductService;
import japhet.sales.service.IUserInformationService;
import japhet.sales.service.IUserProductHistorialService;
import japhet.sales.service.IUserSearchService;
import japhet.sales.service.IUserService;

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
	private IUserSearchService userSearchService;
	
	@EJB
	private IUserService userService;
	
	@EJB
	private IUserInformationService userInformationService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	@EJB
	private IUserProductHistorialService userProductHistorialService;
	
	//View properties
	private List<Product> availableProducts;
	private String searchedWords;
	private Boolean showQuestionVideo;
	
	@PostConstruct
	private void init(){
		try {
			logger.info("Initializing HomeMB...");
			//Initialize products
			setAvailableProducts(productService.getAllAvailableProducts());
			setShowQuestionVideo(false);
			//Is required to update the user information on each page refresh to validate the user categories
			final Long P_USER_ID = getLoggedUser().getUserId();
			User user = userService.getUser(P_USER_ID);
			if(getLoggedUser() != null) {
				//If the user is an admin don't redirect to the categories page
				if(getLoggedUser().getRole().getRoleId() != Roles.ADMINISTRATOR.getId()) {
					if(user.getCategories() == null ||
							user.getCategories().size() == 0) {
						redirect(CATEGORIES_REGISTRATION_URL);
					}
				}
				//Show the pending user information only if the current user have a user role
				UserInformation userInformation = userInformationService
						.getUserInformation(user.getUserId());
				if(getLoggedUser().getRole().getRoleId() == Roles.USER.getId()) {
					if(userInformation == null || !userInformation.validUserInformation()) {
						showPendingUserInformationMessage();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error at initializing the HomeMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	/**
	 * Searches for the products that matches the user criteria.
	 */
	public void searchProducts(){
		final String ERROR_MSG = 
				String.format("Error while searching products which matches: %s.", searchedWords);
		try {
			logger.info(String.format("Searching products which matches: %s...", searchedWords));
			UserSearch userSearch = new UserSearch();
			String wildcardedSearch = wildcardParameter(searchedWords);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(SEARCHED_WORDS, wildcardedSearch);
			setAvailableProducts(productService.getSearchedProducts(parameters));
			//Persist search historical
			userSearch.setSearchString(searchedWords);
			userSearch.setUser(getLoggedUser());
			userSearch.setSearchDate(new Date());
			userSearchService.insertUserSearch(userSearch);
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
	
	/**
	 * Updates the UI and redirects to the Home 
	 * with the filtered Products from the selected Categeroy.
	 * @param category Category to be filtered.
	 */
	public void filterProductsByCategory(Category category) {
		final short CAT_ID = ((category != null 
				&& category.getCategoryId() != null) ? category.getCategoryId() : -1);
		final String ERROR_MSG = String
				.format("Error while searching products with Category: %d.", CAT_ID);
		try {
			final String INFO_MSG = String
					.format("Searching products with Category: %d...", CAT_ID);
			logger.info(INFO_MSG);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(CATEGORY_ID, category.getCategoryId());
			setAvailableProducts(productService.getAvailableProductsByCategory(parameters));
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
	
	/**
	 * Redirects a user to the vendors website if this is logged into the
	 * platform.
	 * @param url URL from the webservice to consume on the vendors end
	 * @param productKey Product Key from our DB
	 */
	public void redirectToSalesSite(Product product){
		//Data for the product user historial
		String url = product.getUrl();
		String productKey = product.getProductKey();
		User user = getLoggedUser();
		//Url String Builder
		StringBuilder urlBldr = new StringBuilder(url);
		UserProductHistorial userProductHistorial = new UserProductHistorial();
		try {
			/* Historial of the user interest products object */
			userProductHistorial.setClickDate(new Date());
			userProductHistorial.setCompleted(false);
			userProductHistorial.setProduct(product);
			userProductHistorial.setUser(user);
			//If the URL does not contain the param delimiter
			if(!url.contains(PARAM_DELIMITER)) {
				urlBldr.append(PARAM_DELIMITER);
			}
			urlBldr.append(PRODUCT_KEY_PARAM);
			urlBldr.append("=");
			urlBldr.append(productKey);
			//Proceed with the redirection if the user is logged otherwise block it
			if(getLoggedUser() != null &&
					getLoggedUser().getRole().getRoleId() == Roles.USER.getId()) {
				ExternalContext extContext = getExternalContext();
				//Generate the fingerprint
				userProductHistorial.generateUserProductHistorialKey();
				// Persist the historial of the user interest products 
				userProductHistorialService.insertProductHistorial(userProductHistorial);
				//Redirect to the specified URL
				extContext.redirect(urlBldr.toString());
			} else {
				final String USER_WARN_MESSAGE = internationalizationService.
						getI18NMessage(CURRENT_LOCALE, getUNREGISTERED_REDIRECT());
				final String WARN_MESSAGE = String.
						format("An attempt from an unregistered/invalid user to redirect to %s was blocked.", urlBldr.toString());
				showWarnMessage(USER_WARN_MESSAGE, "");
				logger.warn(WARN_MESSAGE);
			}
		} catch (IOException e) {
			final String ERROR_MSG = String.format("An error has ocurred while redirecting to: %s", 
					urlBldr.toString());
			final String USER_ERROR_MSG = internationalizationService.
					getI18NMessage(CURRENT_LOCALE, getREDIRECT_ERROR());
			logger.error(ERROR_MSG, e);
			showErrorMessage(USER_ERROR_MSG, "");
		} catch (Exception e) {
			final String USER_ID = ((user != null) ? user.getUserId().toString() : "null");
			final String ERROR_MSG = String.format("An error has ocurred persisting the UserProductHistorial: %s UID: %s", 
					urlBldr.toString(), USER_ID);
			final String USER_ERROR_MSG = internationalizationService.
					getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR());
			logger.error(ERROR_MSG, e);
			showErrorMessage(USER_ERROR_MSG, "");
		}
	}
	
	private void showPendingUserInformationMessage() throws Exception {
		final String WARN_MSG = internationalizationService
				.getI18NMessage(CURRENT_LOCALE, getPENDING_USER_INFORMATION());
		showWarnMessage(WARN_MSG, "");
	}
	
	/**
	 * Shows or hides the Afore video.
	 */
	public void showHideAforeVideo() {
		showQuestionVideo = !showQuestionVideo;
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

	public Boolean getShowQuestionVideo() {
		return showQuestionVideo;
	}

	public void setShowQuestionVideo(Boolean showQuestionVideo) {
		this.showQuestionVideo = showQuestionVideo;
	}
}
