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
		logger.info("Obtaining all available status...");
		return statusDAO.getAllAvailableStatus();
	}

	@Override
	public List<Status> getAllStatus()   
			throws BusinessServiceException {
		logger.info("Obtaining all status...");
		return statusDAO.getAllStatus();
	}
	
	public Status getStatus(Short statusId)   
			throws BusinessServiceException {
		logger.info("Obtaining status " + statusId + " from the DB...");
		try {
			return statusDAO.select(statusId);
		} catch (Exception e) {
			logger.fatal("Error obtaining status" + statusId + 
					" from the DB.", e);
		}
		return null;
	}
	
	public boolean updateStatus(Status status)   
			throws BusinessServiceException {
		logger.info("Updating status into the DB...");
		try {
			statusDAO.update(status);
			return true;
		} catch (Exception e) {
			logger.fatal("Error updating status into the DB.", e);
		}
		return false;
	}
	
	public boolean deleteStatus(Status status)   
			throws BusinessServiceException {
		logger.info("Deleting status into the DB...");
		try {
			statusDAO.delete(status);
			return true;
		} catch (Exception e) {
			logger.fatal("Error deleting status into the DB.", e);
		}
		return false;
	}
	
	public boolean insertStatus(Status status)   
			throws BusinessServiceException {
		logger.info("Inserting status into the DB...");
		try {
			statusDAO.insert(status);
		} catch (Exception e) {
			logger.fatal("Error inserting status into the DB.", e);
		}
		return false;
	}
}
