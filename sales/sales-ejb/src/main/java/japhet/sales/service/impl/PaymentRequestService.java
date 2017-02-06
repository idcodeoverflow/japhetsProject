package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.impl.PaymentRequestDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.PaymentRequest;
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

	@Override
	public List<PaymentRequest> getPaymentRequestsByUser(Long userId) 
			throws BusinessServiceException {
		logger.info("Getting payment requests by user: " + userId);
		List<PaymentRequest> paymentRequests = null;
		try {
			paymentRequests = paymentRequestDAO.getPaymentRequestsByUser(userId);
		} catch (Exception e) {
			final String ERROR_MSG = "";
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
			final String ERROR_MSG = "";
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
			final String ERROR_MSG = "";
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
			final String ERROR_MSG = "";
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
			final String ERROR_MSG = "";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean insertPaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException {
		logger.info("Inserting payment request...");
		try {
			paymentRequestDAO.insert(paymentRequest);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = "";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

}
