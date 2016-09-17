package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.model.impl.Role;

@Local
public interface IRoleService extends Serializable {
	
	public List<Role> getAllAvailableRoles();
	
	public List<Role> getAllRoles();
	
}
