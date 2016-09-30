package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.model.impl.Product;

@Local
public interface IProductService extends Serializable {

	public List<Product> getAllAvailableProducts();
	
}
