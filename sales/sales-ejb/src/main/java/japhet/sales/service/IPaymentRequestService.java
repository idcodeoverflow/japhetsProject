package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.PaymentRequest;

@Local
public interface IPaymentRequestService extends Serializable {

	public List<PaymentRequest> getPaymentRequestsByUser(Long userId) 
			throws BusinessServiceException;
	
	public List<PaymentRequest> getPaymentRequestsByStatus(Short statusId) 
			throws BusinessServiceException;
	
	public PaymentRequest getPaymentRequest(Long paymentRequestId) 
			throws BusinessServiceException;
	
	public boolean updatePaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException;
	
	public boolean deletePaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException;
	
	public boolean insertPaymentRequest(PaymentRequest paymentRequest) 
			throws BusinessServiceException;
}
