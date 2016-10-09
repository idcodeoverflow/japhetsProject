package japhet.sales.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.ProductDAO;
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
			logger.severe("Error obtaining product " + productId 
					+ " from the DB. \n" + e.getStackTrace());
		}
		return null;
	}
	
	public boolean updateProduct(Product product) {
		logger.info("Updating product into the DB...");
		try {
			productDAO.update(product);
			return true;
		} catch (Exception e) {
			logger.severe("Error updating product into the DB. \n" + e.getStackTrace());
		}
		return false;
	}
	
	public boolean deleteProduct(Product product) {
		logger.info("Deleting product into the DB...");
		try {
			productDAO.delete(product);
			return true;
		} catch (Exception e) {
			logger.severe("Error deleting product into the DB. \n" + e.getStackTrace());
		}
		return false;
	}
	
	public boolean insertProduct(Product product) {
		logger.info("Inserting product into the DB...");
		try {
			productDAO.insert(product);
			return true;
		} catch (Exception e) {
			logger.severe("Error inserting product into the DB. \n" + e.getStackTrace());
		}
		return false;
	}

}
