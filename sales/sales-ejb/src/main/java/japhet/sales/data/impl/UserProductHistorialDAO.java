package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.UserProductHistorial;

@Stateless
public class UserProductHistorialDAO extends GenericDAO
	<UserProductHistorial, Long> {

	public UserProductHistorialDAO(Class<UserProductHistorial> type, Class<Long> key) {
		super(type, key);
	}

}
