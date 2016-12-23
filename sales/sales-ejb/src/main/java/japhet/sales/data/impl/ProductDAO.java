package japhet.sales.data.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.Product;

import org.apache.log4j.Logger;

@Stateless
public class ProductDAO extends GenericDAO<Product, Long> {

	@Inject
	private Logger logger;
	
	public ProductDAO() {
		super(Product.class, Long.class);
	}
	
	public List<Product> getAvailableProducts() 
			throws DataLayerException {
		List<Product> products = null;
		logger.info("Obtaining all available products from the DB...");
		try {
			products = executeQuery(QueryNames.GET_AVAILABLE_PRODUCTS, null);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining available products from the DB.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return products;
	}
	
	public List<Product> getSearchedProducts(Map<String, Object> parameters) 
			throws DataLayerException {
		List<Product> products = null;
		logger.info("Obtaining searched products from the DB...");
		try {
			//Add the wildcards to the searchedWords parameter
			String param = (String)parameters.get(SEARCHED_WORDS);
			parameters.put(SEARCHED_WORDS, param);
			//Query the DB.
			products = executeQuery(QueryNames.GET_SEARCHED_PRODUCTS, parameters);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining the searched products from the DB.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return products;
	}
}
