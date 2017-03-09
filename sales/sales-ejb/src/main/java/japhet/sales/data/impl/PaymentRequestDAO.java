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
	
	/**
	 * Default constructor.
	 */
	public PaymentRequestDAO(){
		super(PaymentRequest.class, Long.class);
	}
	
	/**
	 * Obtains a list of payments requests made by a certain user.
	 * @param userId user who made the payment requests
	 * @return a list of payment request from the user
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Obtains a list of payment requests that matches a status
	 * specified.
	 * @param statusId status to match in the DB.
	 * @return list of payment requests that matches the status.
	 * @throws DataLayerException
	 */
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
	
	/**
	 * Obtains a list of payment requests requested to a company.
	 * @param params parameters to pass to the query.
	 * @return a list of payment request that matches the company.
	 * @throws DataLayerException
	 */
	public List<PaymentRequest> getPaymentRequestsByCompany(Map<String, Object> params) 
			throws DataLayerException {
		List<PaymentRequest> paymentRequests = null;
		long P_COMP_ID = ((params != null && params.get(COMPANY_ID) != null) ? (Long)params.get(COMPANY_ID) : -1L);
		short P_STAT_ID = ((params != null && params.get(STATUS_ID) != null) ? (Short)params.get(STATUS_ID) : -1);
		final String INFO_MSG = String.format("Looking for Payment Requests from the Company: %d and with status %s...", P_COMP_ID, P_STAT_ID);
		final String ERROR_MSG = String.format("Error while looking for Payment Requests from the Company: %d and with status %s.", P_COMP_ID, P_STAT_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequests = executeQuery(GET_PAYMENT_REQUESTS_BY_COMPANY, params);
		} catch (Exception e) {
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return paymentRequests;
	}
}
