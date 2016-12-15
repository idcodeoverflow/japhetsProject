package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Status;

@Local
public interface IStatusService extends Serializable {

	public List<Status> getAllAvailableStatus() 
			throws BusinessServiceException;
	
	public List<Status> getAllStatus() 
			throws BusinessServiceException;
	
}
