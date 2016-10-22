package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.UserProductHistorial;

@Local
public interface IUserProductHistorial extends Serializable {

	public List<UserProductHistorial> getHistorialByUser(Map<String, Object> params) 
			throws BusinessServiceException;

	public List<UserProductHistorial> getHistorialByProduct(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public void insertProductHistorial(UserProductHistorial userProductHistorial) 
			throws BusinessServiceException;
	
	public void updateProductHistorial(UserProductHistorial userProductHistorial) 
			throws BusinessServiceException;
	
	public void deleteProductHistorial(UserProductHistorial userProductHistorial) 
			throws BusinessServiceException;
	
	public void getProductHistorialById(Long historialId) 
			throws BusinessServiceException;
	
}
