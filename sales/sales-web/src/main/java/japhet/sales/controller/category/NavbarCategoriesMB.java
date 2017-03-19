package japhet.sales.controller.category;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Category;
import japhet.sales.service.ICategoryService;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@ManagedBean
@ViewScoped
public class NavbarCategoriesMB extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -672038571966762641L;

	//EJB
	@EJB
	private ICategoryService categoryService;
	
	@Inject
	private Logger logger;
	
	//View properties
	private List<Category> categories;
	
	/**
	 * Initializes the content of this MB.
	 */
	@PostConstruct
	private void init() {
		final String INFO_MSG = "Initializing NavbarCategoriesMB...";
		try {
			logger.info(INFO_MSG);
			setCategories(categoryService.getAllAvailableCategories());
		} catch (Exception e) {
			final String ERROR_MSG = "An error has ocurred while initializing NavbarCategoriesMB.";
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}

	/**
	 * @return the categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}	
}
