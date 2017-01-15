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
		logger.info("Obtaining all available categories from the DB...");
		List<Category> categories = null;
		try {
			categories = categoryDAO.getAllAvailableCategories();
		} catch (Exception e) {
			final String errorMsg = "Error obtaining all available categories.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return categories;
	}

	@Override
	public List<Category> getAllCategories()   
			throws BusinessServiceException {
		logger.info("Obtaining all categories from the DB...");
		List<Category> categories = null;
		try {
			categories = categoryDAO.getAllCategories();
		} catch (Exception e) {
			final String errorMsg= "Error obtaining all categories.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return categories;
	}
	
	@Override
	public boolean insertCategory(Category category)   
			throws BusinessServiceException {
		logger.info("Inserting category...");
		try {
			categoryDAO.insert(category);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error inserting category:";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public Category findCategory(Short categoryId)   
			throws BusinessServiceException {
		try {
			return categoryDAO.select(categoryId);
		} catch (Exception e) {
			final String errorMsg = "Error finding category: " + categoryId;
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public boolean updateCategory(Category category)   
			throws BusinessServiceException {
		try {
			categoryDAO.update(category);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error updating category: " + 
					stringifyCategory(category);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	@Override
	public boolean deleteCategory(Category category)   
			throws BusinessServiceException {
		try {
			categoryDAO.delete(category);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error deleting category: " + 
					stringifyCategory(category);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	private Short stringifyCategory(Category category) {
		return ((category != null && category.getCategoryId() != null) ? 
				category.getCategoryId() : null);
	}

}
