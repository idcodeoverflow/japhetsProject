package japhet.sales.service.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserProductHistorialDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.UserProductHistorial;
import japhet.sales.service.IUserProductHistorialService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class UserProductHistorialService implements IUserProductHistorialService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2632136770021767074L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserProductHistorialDAO userProductHistorialDAO;

	@Override
	public List<UserProductHistorial> getHistorialByUser(Map<String, Object> params)   
			throws BusinessServiceException {
		logger.info("Getting historial by user id...");
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getHistorialByUser(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting the historial by the userId.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorials;
	}

	@Override
	public List<UserProductHistorial> getHistorialByProduct(Map<String, Object> params)   
			throws BusinessServiceException {
		logger.info("Getting historial by product id...");
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getHistorialByProduct(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting the historial by the productId.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorials;
	}

	@Override
	public void insertProductHistorial(UserProductHistorial userProductHistorial)   
			throws BusinessServiceException {
		logger.info("Inserting the product historial related to the user...");
		try {
			userProductHistorialDAO.insert(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error inserting the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void updateProductHistorial(UserProductHistorial userProductHistorial)   
			throws BusinessServiceException {
		logger.info("Updating the product historial related to the user...");
		try {
			userProductHistorialDAO.update(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error updating the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void deleteProductHistorial(UserProductHistorial userProductHistorial)   
			throws BusinessServiceException {
		logger.info("Deleting the product historial related to the user...");
		try {
			userProductHistorialDAO.delete(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error deleting the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public UserProductHistorial getProductHistorialById(Long historialId)   
			throws BusinessServiceException {
		logger.info("Getting the product historial related to the user...");
		UserProductHistorial userProductHistorial = null;
		try {
			userProductHistorial = userProductHistorialDAO.select(historialId);
		} catch (Exception e) {
			final String errorMsg = "Error getting the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorial;
	}
	
	
}
