package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.model.impl.Product;

import org.apache.log4j.Logger;

@Stateless
public class ProductDAO extends GenericDAO<Product, Long> {

	@Inject
	private Logger logger;
	
	public ProductDAO() {
		super(Product.class, Long.class);
	}
	
	public List<Product> getAvailableProducts(){
		List<Product> products = null;
		logger.info("Obtaining all available products from the DB...");
		try {
			products = executeQuery(QueryNames.GET_AVAILABLE_PRODUCTS, null);
		} catch (Exception e) {
			logger.fatal("Error while obtaining available products from the DB.", e);
		}
		return products;
	}

}
