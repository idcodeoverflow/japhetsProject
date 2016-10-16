package japhet.sales.controller.upload;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Category;
import japhet.sales.service.ICategoryService;

@NoneScoped
@ManagedBean
public class CategoryMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -209520882103133937L;
	
	@EJB
	private ICategoryService categoryService;
	

	public List<Category> getAvailableCategories(){
		return categoryService.getAllAvailableCategories();
	}
	
	
}
