package japhet.sales.service.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.UserProductHistorialDAO;
import japhet.sales.model.impl.UserProductHistorial;
import japhet.sales.service.IUserProductHistorial;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class UserProductHistorialService implements IUserProductHistorial {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2632136770021767074L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserProductHistorialDAO userProductHistorialDAO;

	@Override
	public List<UserProductHistorial> getHistorialByUser(Map<String, Object> params) {
		logger.info("Getting historial by user id...");
		return userProductHistorialDAO.getHistorialByUser(params);
	}

	@Override
	public List<UserProductHistorial> getHistorialByProduct(Map<String, Object> params) {
		logger.info("Getting historial by product id...");
		return userProductHistorialDAO.getHistorialByProduct(params);
	}

	@Override
	public void insertProductHistorial(UserProductHistorial userProductHistorial) {
		logger.info("Inserting the product historial related to the user...");
		try {
			userProductHistorialDAO.insert(userProductHistorial);
		} catch (Exception e) {
			logger.fatal("Error inserting the product historial related to the user.", e);
		}
	}

	@Override
	public void updateProductHistorial(UserProductHistorial userProductHistorial) {
		logger.info("Updating the product historial related to the user...");
		try {
			userProductHistorialDAO.update(userProductHistorial);
		} catch (Exception e) {
			logger.fatal("Error updating the product historial related to the user.", e);
		}
	}

	@Override
	public void deleteProductHistorial(UserProductHistorial userProductHistorial) {
		logger.info("Deleting the product historial related to the user...");
		try {
			userProductHistorialDAO.delete(userProductHistorial);
		} catch (Exception e) {
			logger.fatal("Error deleting the product historial related to the user.", e);
		}
	}

	@Override
	public void getProductHistorialById(Long historialId) {
		logger.info("Getting the product historial related to the user...");
		try {
			userProductHistorialDAO.select(historialId);
		} catch (Exception e) {
			logger.fatal("Error getting the product historial related to the user.", e);
		}
	}
	
	
}
