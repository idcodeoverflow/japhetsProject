package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.Role;

@Stateless
public class RoleDAO extends GenericDAO<Role, Short> {

	public RoleDAO() {
		super(Role.class, Short.class);
	}

}
