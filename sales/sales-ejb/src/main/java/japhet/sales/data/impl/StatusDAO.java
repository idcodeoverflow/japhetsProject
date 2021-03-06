package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.Status;

import org.apache.log4j.Logger;

@Stateless
public class StatusDAO extends GenericDAO<Status, Short> {

	@Inject
	private Logger logger;
	
	public StatusDAO() {
		super(Status.class, Short.class);
	}
	
	public List<Status> getAllStatus() 
			throws DataLayerException {
		List<Status> status = null;
		try {
			logger.info("Obtaining all the status from the DB...");
			status = executeQuery(QueryNames.GET_ALL_STATUS, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining all the status from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return status;
	}
	
	public List<Status> getAllAvailableStatus() 
			throws DataLayerException {
		List<Status> status = null;
		try {
			logger.info("Obtaining all the available status from the DB...");
			status = executeQuery(QueryNames.GET_ALL_AVAILABLE_STATUS, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining all the available status from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return status;
	}
}
