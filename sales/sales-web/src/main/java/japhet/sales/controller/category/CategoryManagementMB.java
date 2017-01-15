package japhet.sales.controller.category;

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
import japhet.sales.model.impl.Category;
import japhet.sales.service.ICategoryService;
import japhet.sales.service.IUserService;

@ManagedBean
@ViewScoped
public class CategoryManagementMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -7111268126074130708L;

	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private ICategoryService categoryService;
	
	@EJB
	private IUserService userService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	//View properties
	private List<Category> categories;
	private List<Category> userCategories;
	private Map<Short, Boolean> categoriesCache;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing categories manager...");
			categoriesCache = new HashMap<>();
			Long userId = getLoggedUser().getUserId();
			categories = categoryService.getAllAvailableCategories();
			userCategories = (userService.getUser(userId)).getCategories();
			if(userCategories != null) {
				//Generate cache of the user categories
				for(Category category : userCategories) {
					categoriesCache.put(category.getCategoryId(), true);
				}
			}
			//Mark as checked the categories persisted by the user
			for(Category category : categories) {
				if(categoriesCache.get(category.getCategoryId()) != null) {
					category.setChecked(true);
				}
			}
		} catch (Exception e) {
			logger.error("Error while initializing the categories manager.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	public void saveFavoriteCategories() {
		Long userId = getLoggedUser().getUserId();
		Map<String, Object> params = new HashMap<>();
		final String ERROR_MSG = String.
				format("Error saving the user: %d categories.", userId);
		try {
			params.put(P_USER_ID, userId);
			params.put(P_CATEGORIES_LIST, categories);
			userService.modifyUserCategories(params);
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
