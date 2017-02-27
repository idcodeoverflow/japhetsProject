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
		logger.info("Getting payment requests by user: " + userId);
		List<PaymentRequest> paymentRequests = null;
		try {
			paymentRequests = paymentRequestDAO.getPaymentRequestsByUser(userId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting payment requests by user.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequests;
	}

	@Override
	public List<PaymentRequest> getPaymentRequestsByStatus(Short statusId) 
			throws BusinessServiceException {
		logger.info("Getting payment requests by status: " + statusId);
		List<PaymentRequest> paymentRequests = null;
		try {
			paymentRequests = paymentRequestDAO.getPaymentRequestsByStatus(statusId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting payment requests by status.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequests;
	}

	@Override
	public PaymentRequest getPaymentRequest(Long paymentRequestId) 
			throws BusinessServiceException {
		logger.info("Getting payment request: " + paymentRequestId);
		PaymentRequest paymentRequest = null;
		try {
			paymentRequest = paymentRequestDAO.select(paymentRequestId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting payment request.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return paymentRequest;
	}

	@Override
	public boolean updatePaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		logger.info("Updating payment request: " + paymentRequest.getPaymentRequestId());
		try {
			paymentRequestDAO.update(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error while updating payment request.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean deletePaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		logger.info("Deleting payment request: " + paymentRequest.getPaymentRequestId());
		try {
			paymentRequestDAO.delete(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error while deleting payment request.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean insertPaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		Long id = null;
		if(paymentRequest != null) {
			id = paymentRequest.getPaymentRequestId();
		}
		final StringBuilder INFO_MSG = new StringBuilder("Inserting payment request: ");
		INFO_MSG.append(id);
		INFO_MSG.append("...");
		logger.info(INFO_MSG.toString());
		try {
			paymentRequestDAO.insertAndFlush(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "Error while inserting payment request.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public short generatePaymentRequest(Map<String, Object> params) 
			throws BusinessServiceException {
		logger.info("Generating payment request...");
		short rowsUpdated = 0;
		PaymentRequest paymentRequest = null;
		Map<String, Object> daoParams = new HashMap<>();
		try {
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
			final String ERROR_MSG = "Error while generating a payment request.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return rowsUpdated;
	}
}
