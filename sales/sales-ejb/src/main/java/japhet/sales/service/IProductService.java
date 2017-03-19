package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidDateRangeException;
import japhet.sales.model.impl.Product;

@Local
public interface IProductService extends Serializable {

	public List<Product> getAllAvailableProducts() 
			throws BusinessServiceException;
	
	public List<Product> getSearchedProducts(Map<String, Object> parameters) 
			throws BusinessServiceException;
	
	public Product getProductByKey(Map<String, Object> parameters)
			throws BusinessServiceException;
	
	public List<Product> getAvailableProductsFromCompany(Map<String, Object> parameters)
			throws BusinessServiceException;
	
	public Product getProduct(Long productId) 
			throws BusinessServiceException;
	
	public boolean updateProduct(Product product) 
			throws BusinessServiceException;
	
	public boolean updateProductAndFlush(Product product)   
			throws BusinessServiceException;
	
	public boolean deleteProduct(Product product) 
			throws BusinessServiceException;
	
	public boolean insertProduct(Product product) 
			throws InvalidDateRangeException, BusinessServiceException;
	
	public List<Product> getAvailableProductsByCategory(Map<String, Object> parameters) 
			throws BusinessServiceException;
}
