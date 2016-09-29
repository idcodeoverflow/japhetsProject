package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import japhet.sales.model.impl.UserProductHistorial;

public interface IUserProductHistorial extends Serializable {

	public List<UserProductHistorial> getHistorialByUser(Map<String, Object> params);

	public List<UserProductHistorial> getHistorialByProduct(Map<String, Object> params);
	
	public void insertProductHistorial(UserProductHistorial userProductHistorial);
	
	public void updateProductHistorial(UserProductHistorial userProductHistorial);
	
	public void deleteProductHistorial(UserProductHistorial userProductHistorial);
	
	public void getProductHistorialById(Long historialId);
	
}
