package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Role;

@Local
public interface IRoleService extends Serializable {
	
	public List<Role> getAllAvailableRoles() 
			throws BusinessServiceException;
	
	public List<Role> getAllRoles() 
			throws BusinessServiceException;
	
	public List<Role> getAvailableRoles() 
			throws BusinessServiceException;

	public void setAvailableRoles(List<Role> availableRoles) 
			throws BusinessServiceException;
}
