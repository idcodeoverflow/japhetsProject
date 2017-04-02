/**
 * 
 */
package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.PaybackProtest;

/**
 * @author David Israel Garcia Alcazar
 *
 */

@Local
public interface IPaybackProtestService extends Serializable {

	public List<PaybackProtest> getPaybackProtestsByCompany(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public List<PaybackProtest> getPaybackProtestsByUser(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public List<PaybackProtest> getPaybackProtestsByStatus(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public List<PaybackProtest> getAllPaybackProtests() 
			throws BusinessServiceException;
	
	public boolean updatePaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException;
	
	public boolean deletePaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException;
	
	public PaybackProtest insertPaybackProtest(PaybackProtest paybackProtest) 
			throws BusinessServiceException;
	
	public PaybackProtest selectPaybackProtest(Long paybackProtestId) 
			throws BusinessServiceException;
}
