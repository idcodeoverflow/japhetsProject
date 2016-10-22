package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.State;

@Local
public interface IStateService extends Serializable {

	public List<State> getAllStates() 
			throws BusinessServiceException;
	
	public State getState(Short stateId) 
			throws BusinessServiceException;
	
}
