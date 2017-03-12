package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.Category;

import org.apache.log4j.Logger;

@Stateless
public class CategoryDAO extends GenericDAO<Category, Short>{

	@Inject
	private Logger logger;
	
	public CategoryDAO() {
		super(Category.class, Short.class);
	}
	
	public List<Category> getAllCategories() 
			throws DataLayerException {
		List<Category> categories = null;
		try {
			logger.info("Obtaining all the categories from the DB.");
			categories = executeQuery(QueryNames.GET_ALL_CATEGORIES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all the categories from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return categories;
	}
	
	public List<Category> getAllAvailableCategories() 
			throws DataLayerException {
		List<Category> categories = null;
		try {
			logger.info("Obtaining all the categories from the DB.");
			categories = executeQuery(QueryNames.GET_ALL_AVAILABLE_CATEGORIES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all the categories from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return categories;
	}
	
}
