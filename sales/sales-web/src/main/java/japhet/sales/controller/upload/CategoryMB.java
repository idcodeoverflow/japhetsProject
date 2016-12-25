package japhet.sales.controller.upload;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.Category;
import japhet.sales.service.ICategoryService;

@NoneScoped
@ManagedBean
public class CategoryMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -209520882103133937L;
	
	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private ICategoryService categoryService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	public List<Category> getAvailableCategories(){
		List<Category> categories = null;
		try {
			categories = categoryService.getAllAvailableCategories();
		} catch (Exception e) {
			logger.fatal("Error while obtaining all available categories.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
		return categories;
	}
}
