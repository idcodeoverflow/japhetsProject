package japhet.sales.service.impl;

import static japhet.sales.data.QueryParameters.*;

import java.util.Date;
import java.util.HashMap;
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
import japhet.sales.data.impl.PaymentRequestDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.PaymentRequest;
import japhet.sales.model.impl.Status;
import japhet.sales.service.IBuyProofService;
import japhet.sales.service.IPaymentRequestService;

@LocalBean
@Stateless
public class PaymentRequestService 
	implements IPaymentRequestService {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -6347764302513412162L;

	@Inject
	private Logger logger;
	
	@EJB
	private PaymentRequestDAO paymentRequestDAO;
	
	@EJB
	private IBuyProofService buyProofService;

	@Override
	public List<PaymentRequest> getPaymentRequestsByUser(Long userId) 
			throws BusinessServiceException {
		List<PaymentRequest> paymentRequests = null;
		final long P_USER_ID = ((userId != null) ? userId : -1L);
		final String INFO_MSG = String.format("Getting payment requests by user: %d...", P_USER_ID);
		final String ERROR_MSG = String.format("Error while getting payment requests by user: %d.", P_USER_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequests = paymentRequestDAO.getPaymentRequestsByUser(userId);
		} catch (Exception e) {
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequests;
	}

	@Override
	public List<PaymentRequest> getPaymentRequestsByStatus(Short statusId) 
			throws BusinessServiceException {
		List<PaymentRequest> paymentRequests = null;
		final short P_STAT_ID = ((statusId != null) ? statusId : -1);
		final String INFO_MSG = String.format("Getting payment requests by status: %d...", P_STAT_ID);
		final String ERROR_MSG = String.format("Error while getting payment requests by status: %d.", P_STAT_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequests = paymentRequestDAO.getPaymentRequestsByStatus(statusId);
		} catch (Exception e) {
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequests;
	}

	@Override
	public List<PaymentRequest> getPaymentRequestsByCompany(Map<String, Object> params) 
			throws BusinessServiceException {
		List<PaymentRequest> paymentRequests = null;
		final long P_COMP_ID = ((params != null && params.get(COMPANY_ID) != null) ? (Long)params.get(COMPANY_ID) : -1L);
		final String INFO_MSG = String.format("Obtaining payment requests by company: %d...", P_COMP_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequests = paymentRequestDAO.getPaymentRequestsByCompany(params);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error obtaining payment requests by company: %d...", P_COMP_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequests;
	}
	
	@Override
	public PaymentRequest getPaymentRequest(Long paymentRequestId) 
			throws BusinessServiceException {
		PaymentRequest paymentRequest = null;
		final long P_PREQ_ID = ((paymentRequestId != null) ? paymentRequestId : -1L);
		final String INFO_MSG = String.format("Getting payment request: %d...", P_PREQ_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequest = paymentRequestDAO.select(paymentRequestId);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while getting payment request: %d.", P_PREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequest;
	}

	@Override
	public boolean updatePaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		final long P_PREQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG =  String.format("Updating payment request: %d...", P_PREQ_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequestDAO.update(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while updating payment request: %d.", P_PREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean deletePaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		final long P_PREQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("Deleting payment request: %d.", P_PREQ_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequestDAO.delete(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while deleting payment request %d...", P_PREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean insertPaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		final Long P_PREQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("Inserting payment request: %d...", P_PREQ_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequestDAO.insertAndFlush(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while inserting payment request: %d.", P_PREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public short generatePaymentRequest(Map<String, Object> params) 
			throws BusinessServiceException {
		short rowsUpdated = 0;
		final long P_PREQ_ID = ((params != null 
				&& params.get(PAYMENT_REQUEST_ID) != null) ? (Long)params.get(PAYMENT_REQUEST_ID) : -1L);
		final String INFO_MSG = String.format("Generating payment request: %d...", P_PREQ_ID);
		PaymentRequest paymentRequest = null;
		Map<String, Object> daoParams = new HashMap<>();
		try {
			logger.info(INFO_MSG);
			//Populate parameters
			daoParams.put(STATUS_ID, Statuses.ON_PAYMENT_REQUEST.getId());
			daoParams.put(LAST_UPDATE, new Date());
			daoParams.put(BUY_PROOFS_TO_UPDATE, params.get(BUY_PROOFS_TO_UPDATE));
			daoParams.put(PAYMENT_REQUEST_ID, params.get(PAYMENT_REQUEST_ID));
			paymentRequest = (PaymentRequest) params.get(PAYMENT_REQUEST);
			//Call services and DAO inserts
			this.insertPaymentRequest(paymentRequest);
			buyProofService.updateBuyProofsBatch(daoParams);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while generating a payment request: %d.", P_PREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return rowsUpdated;
	}
	
	public void confirmPaymentRequest(PaymentRequest paymentRequest)
			throws BusinessServiceException {
		final long PMNT_REQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("Confirming PaymentRequest: %d...", PMNT_REQ_ID);
		try {
			logger.info(INFO_MSG);
			final short STAT_ID = Statuses.PAYED.getId();
			Status status = new Status();
			status.setStatusId(STAT_ID);
			paymentRequest.setStatus(status);
			paymentRequestDAO.update(paymentRequest);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while confirming PaymentRequest: %d.", PMNT_REQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	public void rejectPaymentRequest(PaymentRequest paymentRequest)
			throws BusinessServiceException {
		final long PMNT_REQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("Rejecting PaymentRequest: %d...", PMNT_REQ_ID);
		try {
			logger.info(INFO_MSG);
			final short STAT_ID = Statuses.DENIED.getId();
			Status status = new Status();
			status.setStatusId(STAT_ID);
			paymentRequest.setStatus(status);
			paymentRequestDAO.update(paymentRequest);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while rejecting PaymentRequest: %d.", PMNT_REQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
}
