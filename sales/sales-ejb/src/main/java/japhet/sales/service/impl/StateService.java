package japhet.sales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import japhet.sales.data.impl.StateDAO;
import japhet.sales.model.impl.State;
import japhet.sales.service.IStateService;

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
		logger.info("Obtaining all states from the DB..");
		sortedStates = stateDAO.getAllStates();
		allStates = new HashMap<>();
		for (State state : sortedStates) {
			allStates.put(state.getStateId(), state);
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
	public State getState(Short stateId) {
		State state = null;
		logger.info("Obtaining state: " + stateId);
		try {
			state = allStates.get(stateId);
		} catch (Exception e) {
			logger.severe("Error obtaining state: " + stateId + 
					"\n" + e.getStackTrace());
		}
		return state;
	}

}
