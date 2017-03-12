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
	
	/**
	 * Obtains a list with all the products 
	 * available from all the companies.
	 * @return
	 * @throws DataLayerException
	 */
	public List<Product> getAvailableProducts() 
			throws DataLayerException {
		List<Product> products = null;
		try {
			logger.info("Obtaining all available products from the DB...");
			products = executeQuery(QueryNames.GET_AVAILABLE_PRODUCTS, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining available products from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return products;
	}
	
	/**
	 * Obtains a list of available products 
	 * which matches the searched words.
	 * @param parameters
	 * @return
	 * @throws DataLayerException
	 */
	public List<Product> getSearchedProducts(Map<String, Object> parameters) 
			throws DataLayerException {
		List<Product> products = null;
		try {
			logger.info("Obtaining searched products from the DB...");
			//Add the wildcards to the searchedWords parameter
			String param = (String)parameters.get(SEARCHED_WORDS);
			parameters.put(SEARCHED_WORDS, param);
			//Query the DB.
			products = executeQuery(QueryNames.GET_SEARCHED_PRODUCTS, parameters);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining the searched products from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return products;
	}
	
	/**
	 * Obtains a Product object which matches 
	 * with the Product Key specified.
	 * @param parameters
	 * @return
	 * @throws DataLayerException
	 */
	public Product getProductByKey(Map<String, Object> parameters) 
			throws DataLayerException {
		Product product = null;
		List<Product> products = null;
		try {
			logger.info("Obtaining product by Key...");
			//Query the DB.
			products = executeQuery(QueryNames.GET_PRODUCT_BY_KEY, parameters);
			//Get the first element from the list
			if(products != null && !products.isEmpty()) {
				if(products.size() > 1) {
					throw new Exception("There are multiple results for the productKey: " 
							+ parameters.get(PRODUCT_KEY));
				}
				product = products.get(0);
			}
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining the product by Key from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return product;
	}
	
	/**
	 * Obtains a list of available products 
	 * from a certain company.
	 * @param parameters
	 * @return
	 * @throws DataLayerException
	 */
	public List<Product> getAvailableProductsFromCompany(Map<String, Object> parameters) 
			throws DataLayerException {
		List<Product> products = null;
		try {
			logger.info("Obtaining available products from a Company in the DB...");
			//Query the DB.
			products = executeQuery(QueryNames.GET_AVAILABLE_PRODUCTS_BY_COMPANY, parameters);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining available products from a Company in the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return products;
	}
}
