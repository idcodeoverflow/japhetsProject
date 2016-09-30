package japhet.sales.data.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.model.impl.Status;

@Stateless
public class StatusDAO extends GenericDAO<Status, Short> {

	@Inject
	private Logger logger;
	
	public StatusDAO() {
		super(Status.class, Short.class);
	}
	
	public List<Status> getAllStatus(){
		List<Status> status = null;
		logger.info("Obtaining all the status from the DB...");
		try {
			status = executeQuery(QueryNames.GET_ALL_STATUS, null);
		} catch (Exception e) {
			logger.severe("Error while obtaining all the status from the DB.");
		}
		return status;
	}
	
	public List<Status> getAllAvailableStatus(){
		List<Status> status = null;
		logger.info("Obtaining all the available status from the DB...");
		try {
			status = executeQuery(QueryNames.GET_ALL_AVAILABLE_STATUS, null);
		} catch (Exception e) {
			logger.severe("Error while obtaining all the available status from the DB.");
		}
		return status;
	}

}
