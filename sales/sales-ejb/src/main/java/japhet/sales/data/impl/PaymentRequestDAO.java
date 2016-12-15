package japhet.sales.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.PaymentRequest;

@Stateless
public class PaymentRequestDAO 
	extends GenericDAO<PaymentRequest, Long> {

	@Inject
	private Logger logger;
	
	private final String USER_ID = "userId";
	private final String STATUS_ID = "statusId";
	
	public PaymentRequestDAO(){
		super(PaymentRequest.class, Long.class);
	}
	
	public List<PaymentRequest> getPaymentRequestsByUser(long userId) 
			throws DataLayerException {
		List<PaymentRequest> paymentRequests = null;
		Map<String, Object> params = new HashMap<>();
		try {
			logger.info("Get payment requests by user... " + userId);
			params.put(USER_ID, userId);
			paymentRequests = executeQuery(GET_PAYMENT_REQUEST_BY_USER, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining the payment requests of the user : " 
					+ userId + " from the database.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paymentRequests;
	}
	
	public List<PaymentRequest> getPaymentRequestsByStatus(short statusId) 
			throws DataLayerException {
		List<PaymentRequest> paymentRequests = null;
		Map<String, Object> params = new HashMap<>();
		try {
			logger.info("Get payment requests by user... " + statusId);
			params.put(STATUS_ID, statusId);
			paymentRequests = executeQuery(GET_PAYMENT_REQUEST_BY_STATUS, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining the payment requests with status : " 
					+ statusId + " from the database.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paymentRequests;
	}
}
