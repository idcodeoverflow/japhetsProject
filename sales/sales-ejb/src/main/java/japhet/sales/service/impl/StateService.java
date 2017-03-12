package japhet.sales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import japhet.sales.data.impl.StateDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.State;
import japhet.sales.service.IStateService;

import org.apache.log4j.Logger;

@Singleton
@Startup
public class StateService implements IStateService {

	@Inject
	private Logger logger;
	
	@EJB
	private StateDAO stateDAO;
	
	private Map<Short, State> allStates;
	
	private List<State> sortedStates;
	
	@PostConstruct
	public void init(){
		try {
			logger.info("Initializing StateService...\nObtaining all states from the DB..");
			sortedStates = stateDAO.getAllStates();
			allStates = new HashMap<>();
			for (State state : sortedStates) {
				allStates.put(state.getStateId(), state);
			}
		} catch (Exception e) {
			logger.fatal("Error initializing States Service.", e);
		}
	}
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1036437709475395893L;

	@Override
	public List<State> getAllStates() {
		logger.info("Obtaining all states..");
		return sortedStates;
	}

	@Override
	public State getState(Short stateId)   
			throws BusinessServiceException {
		State state = null;
		try {
			logger.info("Obtaining state: " + stateId);
			state = allStates.get(stateId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining state: " + stateId;
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return state;
	}

}
