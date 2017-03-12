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
		List<UserProductHistorial> historial = null;
		try {
			logger.info("Obtaining product historial by user id...");
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_USER, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain the product historial by user id.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		List<UserProductHistorial> historial = null;
		try {
			logger.info("Obtaining completed product historial by user id...");
			historial = executeQuery(GET_COMPLETED_PRDCT_HIST_BY_USER, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain the completed product historial by user id.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return historial;
	}
			
	/**
	 * Obtains the products user historial by user and status.
	 * 
	 * @param params
	 * @return
	 * @throws DataLayerException
	 */
	public List<UserProductHistorial> getCompletedHistorialByUserAndStatus(Map<String, Object> params) 
			throws DataLayerException {
		List<UserProductHistorial> historial = null;
		try {
			logger.info("Obtaining completed product historial by user id and status...");
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_USER_N_STATUS, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain the completed product historial by user id and status.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		List<UserProductHistorial> historial = null;
		try {
			logger.info("Obtaining completed product historial by fingerprint...");
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_FPRINT, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain the completed product historial by fingerprint.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		List<UserProductHistorial> historial = null;
		try {
			logger.info("Obtaining product historial by product id...");
			historial = executeQuery(GET_ALL_PRODUCT_HISTORIAL_BY_PRODUCT, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain the product historial by product id.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		try {
			logger.info("Saving product historial for the user: " 
					+ userProductHistorial.getUser().getUserId());
			insert(userProductHistorial);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to save the product historial for the user.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		try {
			logger.info("Saving product historial for the user: " 
					+ userProductHistorial.getUser().getUserId());
			update(userProductHistorial);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to update the product historial for the user.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		try {
			logger.info("Deleting product historial for the user: " 
					+ userProductHistorial.getUser().getUserId());
			delete(userProductHistorial);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to delete the product historial for the user.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
		try {
			logger.info("Obtaining product historial with id: " 
					+ historialId);
			select(historialId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to delete the product historial by id.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}
}
