package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.impl.ProductDAO;
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
	public List<Product> getAllAvailableProducts() {
		logger.info("Obtaining all available products...");
		return productDAO.getAvailableProducts();
	}

	public Product getProduct(Long productId) {
		logger.info("Obtaining product " + productId + " from the DB...");
		try {
			return productDAO.select(productId);
		} catch (Exception e) {
			logger.fatal("Error obtaining product " + productId 
					+ " from the DB.", e);
		}
		return null;
	}
	
	public boolean updateProduct(Product product) {
		logger.info("Updating product into the DB...");
		try {
			productDAO.update(product);
			return true;
		} catch (Exception e) {
			logger.fatal("Error updating product into the DB.", e);
		}
		return false;
	}
	
	public boolean deleteProduct(Product product) {
		logger.info("Deleting product into the DB...");
		try {
			productDAO.delete(product);
			return true;
		} catch (Exception e) {
			logger.fatal("Error deleting product into the DB.", e);
		}
		return false;
	}
	
	public boolean insertProduct(Product product) throws InvalidDateRangeException {
		logger.info("Inserting product into the DB...");
		try {
			if(product.getStartDate().after(product.getEndDate())) {
				throw new InvalidDateRangeException("The range of dates is not valid.");
			}
			productDAO.insert(product);
			return true;
		} catch (Exception e) {
			logger.fatal("Error inserting product into the DB.", e);
		}
		return false;
	}
}
