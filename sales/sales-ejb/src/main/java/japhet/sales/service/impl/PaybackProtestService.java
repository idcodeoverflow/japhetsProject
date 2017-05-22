package japhet.sales.service.impl;

import static japhet.sales.data.QueryParameters.*;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Statuses;
import japhet.sales.data.impl.BuyProofDAO;
import japhet.sales.data.impl.PaybackProtestDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.PaybackProtest;
import japhet.sales.model.impl.Status;
import japhet.sales.service.IPaybackProtestService;

/**
 * @author David Israel Garcia Alcazar
 *
 */

@LocalBean
@Stateless
public class PaybackProtestService implements IPaybackProtestService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -4223370526506255586L;

	@Inject
	private Logger logger;
	
	@EJB
	private PaybackProtestDAO paybackProtestDAO;
	
	@EJB
	private BuyProofDAO buyProofDAO;
	
	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#getPaybackProtestsByCompany(java.util.Map)
	 */
	@Override
	public List<PaybackProtest> getPaybackProtestsByCompany(Map<String, Object> params)
			throws BusinessServiceException {
		final Long COMP_ID = ((params != null && params.containsKey(COMPANY_ID)) ? (long)params.get(COMPANY_ID) : -1L);
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the PaybackProtest by Company: %d...", COMP_ID);
		try {
			logger.info(INFO_MSG);
			paybackProtests = paybackProtestDAO.getPaybackProtestsByCompany(params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest by Company: %d.", COMP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#getPaybackProtestsByUser(java.util.Map)
	 */
	@Override
	public List<PaybackProtest> getPaybackProtestsByUser(Map<String, Object> params) 
			throws BusinessServiceException {
		final Long COMP_ID = ((params != null && params.containsKey(USER_ID)) ? (long)params.get(USER_ID) : -1L);
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the PaybackProtest by User: %d...", COMP_ID);
		try {
			logger.info(INFO_MSG);
			paybackProtests = paybackProtestDAO.getPaybackProtestsByUser(params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest by User:: %d.", COMP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#getPaybackProtestsByStatus(java.util.Map)
	 */
	@Override
	public List<PaybackProtest> getPaybackProtestsByStatus(Map<String, Object> params) 
			throws BusinessServiceException {
		final Long COMP_ID = ((params != null && params.containsKey(STATUS_ID)) ? (long)params.get(STATUS_ID) : -1L);
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the PaybackProtest by Status: %d...", COMP_ID);
		try {
			logger.info(INFO_MSG);
			paybackProtests = paybackProtestDAO.getPaybackProtestsByStatus(params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest by Status: %d.", COMP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#getAllPaybackProtests()
	 */
	@Override
	public List<PaybackProtest> getAllPaybackProtests() 
			throws BusinessServiceException {
		List<PaybackProtest> paybackProtests = null;
		final String INFO_MSG = String.format("Selecting all the  PaybackProtest...");
		try {
			logger.info(INFO_MSG);
			paybackProtests = paybackProtestDAO.getAllPaybackProtests();
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting all the PaybackProtest.");
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paybackProtests;
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#updatePaybackProtest(japhet.sales.model.impl.PaybackProtest)
	 */
	@Override
	public boolean updatePaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException {
		final long PBP_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		final String INFO_MSG = String.format("Updating PaybackProtest: %d...", PBP_ID);
		try {
			logger.info(INFO_MSG);
			paybackProtestDAO.update(paybackProtest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while updating the PaybackProtest: %d.", PBP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#deletePaybackProtest(java.lang.Long)
	 */
	@Override
	public boolean deletePaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException {
		final long PBP_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		final String INFO_MSG = String.format("Deleting PaybackProtest: %d...", PBP_ID);
		try {
			logger.info(INFO_MSG);
			paybackProtestDAO.delete(paybackProtest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while deleting the PaybackProtest: %d.", PBP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#insertPaybackProtest(japhet.sales.model.impl.PaybackProtest)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean insertPaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException {
		final long PBP_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		final String INFO_MSG = String.format("Inserting PaybackProtest: %d...", PBP_ID);
		try {
			logger.info(INFO_MSG);
			paybackProtestDAO.insert(paybackProtest);
			//Update BuyProof status
			BuyProof buyProof = paybackProtest.getBuyProof();
			Status status = new Status();
			status.setStatusId(Statuses.CASE_RAISED.getId());
			buyProof.setStatus(status);
			buyProofDAO.update(buyProof);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while inserting the PaybackProtest: %d.", PBP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	/* (non-Javadoc)
	 * @see japhet.sales.service.IPaybackProtestService#selectPaybackProtest(japhet.sales.model.impl.PaybackProtest)
	 */
	@Override
	public PaybackProtest selectPaybackProtest(Long paybackProtestId) 
			throws BusinessServiceException {
		PaybackProtest paybackProtest = null;
		final String INFO_MSG = String.format("Selecting PaybackProtest: %d...", paybackProtestId);
		try {
			logger.info(INFO_MSG);
			paybackProtest = paybackProtestDAO.select(paybackProtestId);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while selecting the PaybackProtest: %d.", paybackProtestId);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paybackProtest;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void acceptPaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException {
		final long PROTEST_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		try {
			final String INFO_MSG = String.format("Accepting PaybackProtest: %d...", PROTEST_ID);
			logger.info(INFO_MSG);
			Status status = new Status();
			status.setStatusId(Statuses.VALIDATED.getId());
			paybackProtest.setStatus(status);
			Status statusBproof = new Status();
			statusBproof.setStatusId(Statuses.DENIED.getId());
			BuyProof buyProof = paybackProtest.getBuyProof();
			buyProof.setStatus(statusBproof);
			buyProofDAO.update(buyProof);
			paybackProtestDAO.update(paybackProtest);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while accepting the PaybackProtest: %d.", PROTEST_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void rejectPaybackProtest(PaybackProtest paybackProtest, List<PaybackProtest> paybackProtests) 
			throws BusinessServiceException {
		final long PROTEST_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		try {
			final String INFO_MSG = String.format("Rejecting PaybackProtest: %d...", PROTEST_ID);
			boolean activateBProof = true;
			logger.info(INFO_MSG);
			Status status = new Status();
			status.setStatusId(Statuses.DENIED.getId());
			paybackProtest.setStatus(status);
			paybackProtestDAO.update(paybackProtest);
			//In the scenario that all the payback protests are denied put the buy proof back on payment request
			if(paybackProtests != null) {
				for(PaybackProtest protest : paybackProtests) {
					if(protest.getPaybackProtestId() != paybackProtest.getPaybackProtestId() 
							&& protest.getStatus().getStatusId() != Statuses.DENIED.getId()){
						activateBProof = false;
						break;
					}
				}
			}
			if(activateBProof) {
				Status statusBproof = new Status();
				statusBproof.setStatusId(Statuses.VALIDATION_PENDING.getId());
				BuyProof buyProof = paybackProtest.getBuyProof();
				buyProof.setStatus(statusBproof);
				buyProofDAO.update(buyProof);
			}
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while rejecting the PaybackProtest: %d.", PROTEST_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
}
