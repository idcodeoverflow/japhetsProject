package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.CategoryDAO;
import japhet.sales.except.BusinessServiceException;
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
	public List<Category> getAllAvailableCategories()   
			throws BusinessServiceException {
		List<Category> categories = null;
		try {
			logger.info("Obtaining all available categories from the DB...");
			categories = categoryDAO.getAllAvailableCategories();
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining all available categories.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return categories;
	}

	@Override
	public List<Category> getAllCategories()   
			throws BusinessServiceException {
		List<Category> categories = null;
		try {
			logger.info("Obtaining all categories from the DB...");
			categories = categoryDAO.getAllCategories();
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining all categories.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return categories;
	}
	
	@Override
	public boolean insertCategory(Category category)   
			throws BusinessServiceException {
		try {
			logger.info("Inserting category...");
			categoryDAO.insert(category);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error inserting category:";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public Category findCategory(Short categoryId)   
			throws BusinessServiceException {
		try {
			return categoryDAO.select(categoryId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error finding category: " + categoryId;
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean updateCategory(Category category)   
			throws BusinessServiceException {
		try {
			categoryDAO.update(category);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error updating category: " + 
					stringifyCategory(category);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean deleteCategory(Category category)   
			throws BusinessServiceException {
		try {
			categoryDAO.delete(category);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error deleting category: " + 
					stringifyCategory(category);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	private Short stringifyCategory(Category category) {
		return ((category != null && category.getCategoryId() != null) ? 
				category.getCategoryId() : null);
	}

}
