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
	
	public Status getStatus(Short statusId)   
			throws BusinessServiceException;
	
	public boolean updateStatus(Status status)   
			throws BusinessServiceException;
	
	public boolean deleteStatus(Status status)   
			throws BusinessServiceException;
	
	public boolean insertStatus(Status status)   
			throws BusinessServiceException;
}
