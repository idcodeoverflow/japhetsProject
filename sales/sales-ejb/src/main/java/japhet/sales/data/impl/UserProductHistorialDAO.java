package japhet.sales.data.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.UserProductHistorial;

@Stateless
public class UserProductHistorialDAO extends GenericDAO
	<UserProductHistorial, Long> {

	@Inject
	private Logger logger;
	
	/**
	 * Default construtor.
	 */
	public UserProductHistorialDAO() {
		super(UserProductHistorial.class, Long.class);
	}
	
	/**
	 * Obtains the whole user product historial.
	 * 
	 * @param params
	 * @return
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Obtains the products user historial by user.
	 * 
	 * @param params
	 * @return
	 * @throws DataLayerException
	 */
	public List<UserProductHistorial> getCompletedHistorialByUser(Map<String, Object> params) 
			throws DataLayerException {
		logger.info("Obtaining completed product historial by user id...");
		List<UserProductHistorial> historial = null;
		try {
			historial = executeQuery(GET_COMPLETED_PRDCT_HIST_BY_USER, params);
		} catch (Exception e) {
			final String errorMsg = "Error trying to obtain the completed product historial by user id.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return historial;
	}
	
	/**
	 * Obtains the product user history by fingerprint.
	 * 
	 * @param params
	 * @return
	 * @throws DataLayerException
	 */
	public List<UserProductHistorial> getCompletedHistorialByFingerprint(Map<String, Object> params) 
			throws DataLayerException {
		logger.info("Obtaining completed product historial by fingerprint...");
		List<UserProductHistorial> historial = null;
		try {
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_FPRINT, params);
		} catch (Exception e) {
			final String errorMsg = "Error trying to obtain the completed product historial by fingerprint.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return historial;
	}

	/**
	 * Obtains the whole product historial from one product.
	 * 
	 * @param params
	 * @return
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Inserts a new product historial into the DB.
	 * 
	 * @param userProductHistorial
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Updates an existent product historial.
	 * 
	 * @param userProductHistorial
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Deletes an existent product historial.
	 * 
	 * @param userProductHistorial
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Obtains an existent product historial.
	 * 
	 * @param historialId
	 * @throws DataLayerException
	 */
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
