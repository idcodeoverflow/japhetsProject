package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.CategoryDAO;
import japhet.sales.model.impl.Category;
import japhet.sales.service.ICategoryService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class CategoryService implements ICategoryService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 370714673404237382L;
	
	@Inject
	private Logger logger;
	
	@EJB
	private CategoryDAO categoryDAO;
	
	@Override
	public List<Category> getAllAvailableCategories() {
		logger.info("Obtaining all available categories from the DB...");
		return categoryDAO.getAllAvailableCategories();
	}

	@Override
	public List<Category> getAllCategories() {
		logger.info("Obtaining all categories from the DB...");
		return categoryDAO.getAllCategories();
	}
	
	public boolean insertCategory(Category category) {
		try {
			categoryDAO.insert(category);
			return true;
		} catch (Exception e) {
			logger.fatal("Error inserting category:", e);
		}
		return false;
	}
	
	public Category findCategory(Short categoryId) {
		try {
			return categoryDAO.select(categoryId);
		} catch (Exception e) {
			logger.fatal("Error finding category: " + categoryId, e);
		}
		return null;
	}
	
	public boolean updateCategory(Category category) {
		try {
			categoryDAO.update(category);
			return true;
		} catch (Exception e) {
			logger.fatal("Error finding category: ", e);
		}
		return false;
	}
	
	public boolean deleteCategory(Category category) {
		try {
			categoryDAO.delete(category);
		} catch (Exception e) {
			logger.fatal("Error deleting category: ", e);
		}
		return false;
	}

}
