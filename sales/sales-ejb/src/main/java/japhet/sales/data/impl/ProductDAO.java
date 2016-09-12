package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.Product;

@Stateless
public class ProductDAO extends GenericDAO<Product, Long> {

	public ProductDAO(Class<Product> type, Class<Long> key) {
		super(type, key);
	}

}
