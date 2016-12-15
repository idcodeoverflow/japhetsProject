package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.impl.ProductDAO;
import japhet.sales.except.BusinessServiceException;
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
		logger.info("Obtaining all available products...");
		try {
			products = productDAO.getAvailableProducts();
		} catch (Exception e) {
			final String errorMsg = "Error while getting all available products.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return products;
	}

	public Product getProduct(Long productId)   
			throws BusinessServiceException {
		logger.info("Obtaining product " + productId + " from the DB...");
		Product product = null;
		try {
			product = productDAO.select(productId);
		} catch (Exception e) {
			final String errorMsg = "Error obtaining product " + productId 
					+ " from the DB: " + stringifyProduct(product);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return product;
	}
	
	public boolean updateProduct(Product product)   
			throws BusinessServiceException {
		logger.info("Updating product into the DB...");
		try {
			productDAO.update(product);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error updating product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	public boolean deleteProduct(Product product)   
			throws BusinessServiceException {
		logger.info("Deleting product into the DB...");
		try {
			productDAO.delete(product);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error deleting product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	public boolean insertProduct(Product product) 
			throws InvalidDateRangeException, BusinessServiceException {
		logger.info("Inserting product into the DB...");
		try {
			if(product.getStartDate().after(product.getEndDate())) {
				throw new InvalidDateRangeException("The range of dates is not valid.");
			}
			productDAO.insert(product);
			return true;
		} catch (Exception e) {
			final String errorMsg = "Error inserting product into the DB: " 
					+ stringifyProduct(product);
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}
	
	private Long stringifyProduct(Product product) {
		return ((product != null && product.getProductId() != null) 
				? product.getProductId() : null);
	}
}
