package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.StatusDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Status;
import japhet.sales.service.IStatusService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class StatusService implements IStatusService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -2184440515820862991L;

	@Inject
	private Logger logger;
	
	@EJB
	private StatusDAO statusDAO;
	
	@Override
	public List<Status> getAllAvailableStatus()   
			throws BusinessServiceException {
		List<Status> status = null;
		try {
			logger.info("Obtaining all available status...");
			status = statusDAO.getAllAvailableStatus();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining all the available status.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return status;
	}

	@Override
	public List<Status> getAllStatus()   
			throws BusinessServiceException {
		List<Status> status = null;
		try {
			logger.info("Obtaining all status...");
			status = statusDAO.getAllStatus();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all the Status.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return status;
	}
	
	@Override
	public Status getStatus(Short statusId)   
			throws BusinessServiceException {
		Status status = null;
		try {
			logger.info("Obtaining status " + statusId + " from the DB...");
			status = statusDAO.select(statusId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining status" + statusId + 
					" from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return status;
	}
	
	@Override
	public boolean updateStatus(Status status)   
			throws BusinessServiceException {
		try {
			logger.info("Updating status into the DB...");
			statusDAO.update(status);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error updating status into the DB: " 
					+ stringifyStatus(status);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean deleteStatus(Status status)   
			throws BusinessServiceException {
		try {
			logger.info("Deleting status into the DB...");
			statusDAO.delete(status);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error deleting status into the DB: " 
					+ stringifyStatus(status);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	public boolean insertStatus(Status status)   
			throws BusinessServiceException {
		try {
			logger.info("Inserting status into the DB...");
			statusDAO.insert(status);
		} catch (Exception e) {
			final String ERROR_MSG = "Error inserting status into the DB: " 
					+ stringifyStatus(status);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return false;
	}
	
	private Short stringifyStatus(Status status){
		return ((status != null && status.getStatusId() != null) 
				? status.getStatusId() : null);
	}
}
