package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Category;

@Local
public interface ICategoryService extends Serializable {

	public List<Category> getAllAvailableCategories() 
			throws BusinessServiceException;

	public List<Category> getAllCategories() 
			throws BusinessServiceException;
}
