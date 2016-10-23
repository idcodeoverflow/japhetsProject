package japhet.sales.data.impl;

import static japhet.sales.data.QueryNames.*;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.UserProductHistorial;

import org.apache.log4j.Logger;

@Stateless
public class UserProductHistorialDAO extends GenericDAO
	<UserProductHistorial, Long> {

	@Inject
	private Logger logger;
	
	public UserProductHistorialDAO() {
		super(UserProductHistorial.class, Long.class);
	}
	
	public List<UserProductHistorial> getHistorialByUser(Map<String, Object> params) 
			throws DataLayerException {
		logger.info("Obtaining product historial by user id...");
		List<UserProductHistorial> historial = null;
		try {
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_USER, params);
		} catch (Exception e) {
			final String errorMsg = "Error trying to obtain the product historial by user id.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return historial;
	}

	public List<UserProductHistorial> getHistorialByProduct(Map<String, Object> params) 
			throws DataLayerException {
		logger.info("Obtaining product historial by product id...");
		List<UserProductHistorial> historial = null;
		try {
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_PRODUCT, params);
		} catch (Exception e) {
			final String errorMsg = "Error trying to obtain the product historial by product id.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return historial;
	}
	
	public void insertProductHistorial(UserProductHistorial userProductHistorial) 
			throws DataLayerException {
		logger.info("Saving product historial for the user: " 
				+ userProductHistorial.getUser().getUserId());
		try {
			insert(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to save the product historial for the user.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
	}
	
	public void updateProductHistorial(UserProductHistorial userProductHistorial) 
			throws DataLayerException {
		logger.info("Saving product historial for the user: " 
				+ userProductHistorial.getUser().getUserId());
		try {
			update(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to update the product historial for the user.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
	}
	
	public void deleteProductHistorial(UserProductHistorial userProductHistorial) 
			throws DataLayerException {
		logger.info("Deleting product historial for the user: " 
				+ userProductHistorial.getUser().getUserId());
		try {
			delete(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to delete the product historial for the user.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
	}
	
	public void getProductHistorialById(Long historialId) 
			throws DataLayerException {
		logger.info("Obtaining product historial with id: " 
				+ historialId);
		try {
			select(historialId);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to delete the product historial by id.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
	}
}
