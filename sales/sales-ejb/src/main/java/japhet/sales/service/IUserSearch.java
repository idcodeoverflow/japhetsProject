package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import japhet.sales.model.impl.UserSearch;

public interface IUserSearch extends Serializable {

	public void insertUserSearch(UserSearch userSearch);
	
	public void updateUserSearch(UserSearch userSearch);
	
	public void deleteUserSearch(UserSearch userSearch);
	
	public UserSearch selectUserSearch(Long userSearchId);
	
	public List<UserSearch> getSearchByUser(Map<String, Object> params);
}
