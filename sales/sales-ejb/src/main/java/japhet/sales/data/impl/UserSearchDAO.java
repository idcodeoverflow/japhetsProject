package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.UserSearch;

@Stateless
public class UserSearchDAO extends GenericDAO<UserSearch, Long> {

	public UserSearchDAO(Class<UserSearch> type, Class<Long> key) {
		super(type, key);
	}

}
