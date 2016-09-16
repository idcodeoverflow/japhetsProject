package japhet.sales.data.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.model.impl.Category;

@Stateless
public class CategoryDAO extends GenericDAO<Category, Short>{

	@Inject
	private Logger logger;
	
	public CategoryDAO() {
		super(Category.class, Short.class);
	}
	
	public List<Category> getAllCategories() {
		logger.info("Obtaining all the categories from the DB.");
		List<Category> categories = null;
		try {
			categories = executeQuery(QueryNames.GET_ALL_CATEGORIES, null);
		} catch (Exception e) {
			logger.severe("Error while getting all the categories from the DB. " + 
					e.getStackTrace());
		}
		return categories;
	}
	
	public List<Category> getAllAvailableCategories() {
		logger.info("Obtaining all the categories from the DB.");
		List<Category> categories = null;
		try {
			categories = executeQuery(QueryNames.GET_ALL_AVAILABLE_CATEGORIES, null);
		} catch (Exception e) {
			logger.severe("Error while getting all the categories from the DB. " + 
					e.getStackTrace());
		}
		return categories;
	}
	
}
