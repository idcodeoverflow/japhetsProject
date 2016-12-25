package japhet.sales.controller.registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.City;
import japhet.sales.model.impl.State;
import japhet.sales.service.IStateService;

@ManagedBean
@ApplicationScoped
public class StatesMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5394399062362399715L;

	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private IStateService iStateService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	//GUI attributes
	private Map<Short, State> statesMap;
	private List<State> states;
	
	@PostConstruct
	public void init() {
		try {
			logger.info("Initializing States MB...");
			statesMap = new HashMap<>();
			states = iStateService.getAllStates();
			if(states != null) {
				for(State state : states) {
					statesMap.put(state.getStateId(), state);
				}
			}
		} catch (Exception e) {
			logger.fatal("Error while initializing StatesMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}

	public State getState(Short stateId) {
		State state = null;
		try {
			state = iStateService.getState(stateId);
		} catch (Exception e) {
			logger.fatal("Error while trying to obtain the state: " + stateId);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getPICK_STATE_ERROR()), "");
		}
		return state;
	}
	
	public List<State> getStates() {
		return states;
	}

	public SortedSet<City> getCitiesFromState(Short stateId){
		return statesMap.get(stateId).getCities();
	}

	public void setStatesMap(Map<Short, State> statesMap) {
		this.statesMap = statesMap;
	}

	public State findStateById(Short stateId){
		return getState(stateId);
	}
}
