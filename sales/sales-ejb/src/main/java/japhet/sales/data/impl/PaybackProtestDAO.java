package japhet.sales.data.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.PaybackProtest;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@Stateless
public class PaybackProtestDAO extends GenericDAO<PaybackProtest, Long> {

	@Inject
	private Logger logger;
	
	public PaybackProtestDAO() {
		super(PaybackProtest.class, Long.class);
	}
	
	public List<PaybackProtest> getPaybackProtestsByCompany(Map<String, Object> params)
			throws DataLayerException {
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the PaybackProtest by Company: %d...");
		try {
			logger.info(INFO_MSG);
			paybackProtests = selectList(GET_ALL_PAYBACK_PROTEST_BY_COMPANY, params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest by Company: %d.");
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	public List<PaybackProtest> getPaybackProtestsByUser(Map<String, Object> params) 
			throws DataLayerException {
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the PaybackProtest by User: %d...");
		try {
			logger.info(INFO_MSG);
			paybackProtests = selectList(GET_ALL_PAYBACK_PROTEST_BY_USER, params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest by User:: %d.");
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	public List<PaybackProtest> getPaybackProtestsByStatus(Map<String, Object> params) 
			throws DataLayerException {
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the PaybackProtest by Status: %d...");
		try {
			logger.info(INFO_MSG);
			paybackProtests = selectList(GET_ALL_PAYBACK_PROTEST_BY_STATUS, params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest by Status: %d.");
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	public List<PaybackProtest> getAllPaybackProtests() 
			throws DataLayerException {
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the  PaybackProtest...");
		try {
			logger.info(INFO_MSG);
			paybackProtests = selectList(GET_ALL_PAYBACK_PROTEST, null);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest.");
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	public boolean updatePaybackProtest(PaybackProtest paybackProtest) 
			throws DataLayerException {
		final long PBP_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		final String INFO_MSG = String.format("Updating PaybackProtest: %d...", PBP_ID);
		try {
			logger.info(INFO_MSG);
			update(paybackProtest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while updating the PaybackProtest: %d.", PBP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}

	public boolean deletePaybackProtest(PaybackProtest paybackProtest) 
			throws DataLayerException {
		final long PBP_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		final String INFO_MSG = String.format("Deleting PaybackProtest: %d...", PBP_ID);
		try {
			logger.info(INFO_MSG);
			delete(paybackProtest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while deleting the PaybackProtest: %d.", PBP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}

	public PaybackProtest insertPaybackProtest(PaybackProtest paybackProtest) 
			throws DataLayerException {
		final long PBP_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		final String INFO_MSG = String.format("Inserting PaybackProtest: %d...", PBP_ID);
		try {
			logger.info(INFO_MSG);
			return insert(paybackProtest);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while inserting the PaybackProtest: %d.", PBP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
	}

	public PaybackProtest selectPaybackProtest(Long paybackProtestId) 
			throws DataLayerException {
		PaybackProtest paybackProtest = null;
		final String INFO_MSG = String.format("Selecting PaybackProtest: %d...", paybackProtestId);
		try {
			logger.info(INFO_MSG);
			paybackProtest = select(paybackProtestId);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting the PaybackProtest: %d.", paybackProtestId);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paybackProtest;
	}	
}
