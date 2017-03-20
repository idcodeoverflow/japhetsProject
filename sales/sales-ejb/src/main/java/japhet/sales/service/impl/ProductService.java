package japhet.sales.service.impl;

import static japhet.sales.data.QueryParameters.*;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.impl.ProductDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.DataLayerException;
import japhet.sales.except.InvalidDateRangeException;
import japhet.sales.model.impl.Product;
import japhet.sales.service.IProductService;

@LocalBean
@Stateless
public class ProductService implements IProductService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -1789294062367578106L;

	@Inject
	private Logger logger;
	
	@EJB
	private ProductDAO productDAO;
	
	@Override
	public List<Product> getAllAvailableProducts()   
			throws BusinessServiceException {
		List<Product> products = null;
		try {
			logger.info("Obtaining all available products...");
			products = productDAO.getAvailableProducts();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all available products.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return products;
	}
	
	@Override
	public List<Product> getSearchedProducts(Map<String, Object> parameters) 
			throws BusinessServiceException {
		List<Product> products = null;
		try {
			logger.info("Obtaining searched products...");
			products = productDAO.getSearchedProducts(parameters);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting the searched products.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return products;
	}
	
	@Override
	public Product getProductByKey(Map<String, Object> parameters)
			throws BusinessServiceException {
		final String INFO_MSG = String.format("Obtaining product by Key %s...", parameters.get(PRODUCT_KEY));
		Product product = null;
		try {
			logger.info(INFO_MSG);
			product = productDAO.getProductByKey(parameters);
		} catch (DataLayerException e) {
			final String ERROR_MSG = String.format("Error obtaining product by Key %s", 
					parameters.get(PRODUCT_KEY));
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return product;
	}
	
	@Override
	public List<Product> getAvailableProductsFromCompany(Map<String, Object> parameters)
			throws BusinessServiceException {
		List<Product> products = null;
		final long COMP_ID = ((parameters != null 
				&& parameters.get(COMPANY_ID) != null) ? (Long)parameters.get(COMPANY_ID) : -1L);
		final String INFO_MSG = String.format("Obtaining available Products by Company %d...", COMP_ID);
		try {
			logger.info(INFO_MSG);
			products = productDAO.getAvailableProductsFromCompany(parameters);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while getting the availale Products by Company %d.",  COMP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return products;
	}
	
	@Override
	public List<Product> getAvailableProductsByCategory(Map<String, Object> parameters) 
			throws BusinessServiceException {
		List<Product> products = null;
		final short CAT_ID = ((parameters != null 
				&& parameters.get(CATEGORY_ID) != null) ? (Short)parameters.get(CATEGORY_ID) : -1);
		final String INFO_MSG = String.format("Obtaining available Products by Category %d...", CAT_ID);
		try {
			logger.info(INFO_MSG);
			products = productDAO.getAvailableProductsByCategory(parameters);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while getting the availale Products by Category %d.",  CAT_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return products;
	}

	@Override
	public Product getProduct(Long productId)   
			throws BusinessServiceException {
		Product product = null;
		try {
			logger.info("Obtaining product " + productId + " from the DB...");
			product = productDAO.select(productId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining product " + productId 
					+ " from the DB: " + stringifyProduct(product);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return product;
	}
	
	@Override
	public boolean updateProduct(Product product)   
			throws BusinessServiceException {
		try {
			logger.info("Updating product into the DB...");
			productDAO.update(product);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error updating product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean updateProductAndFlush(Product product)   
			throws BusinessServiceException {
		try {
			logger.info("Updating product into the DB...");
			productDAO.updateAndFlush(product);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error updating product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean deleteProduct(Product product)   
			throws BusinessServiceException {
		try {
			logger.info("Deleting product into the DB...");
			productDAO.delete(product);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error deleting product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean insertProduct(Product product) 
			throws InvalidDateRangeException, BusinessServiceException {
		try {
			logger.info("Inserting product into the DB...");
			if(product.getStartDate().after(product.getEndDate())) {
				throw new InvalidDateRangeException("The range of dates is not valid.");
			}
			productDAO.insert(product);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error inserting product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	private Long stringifyProduct(Product product) {
		return ((product != null && product.getProductId() != null) 
				? product.getProductId() : null);
	}
}
