package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.UserSearch;

@Local
public interface IUserSearch extends Serializable {

	public void insertUserSearch(UserSearch userSearch) 
			throws BusinessServiceException;
	
	public void updateUserSearch(UserSearch userSearch) 
			throws BusinessServiceException;
	
	public void deleteUserSearch(UserSearch userSearch) 
			throws BusinessServiceException;
	
	public UserSearch selectUserSearch(Long userSearchId) 
			throws BusinessServiceException;
	
	public List<UserSearch> getSearchByUser(Map<String, Object> params) 
			throws BusinessServiceException;
}
