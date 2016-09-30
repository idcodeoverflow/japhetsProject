package japhet.sales.controller.registration;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.State;
import japhet.sales.service.IStateService;

@ManagedBean
@ApplicationScoped
public class StatesMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5394399062362399715L;

	@EJB
	private IStateService iStateService;
	
	//GUI attributes
	private List<State> states;
	
	@PostConstruct
	public void init(){
		states = iStateService.getAllStates();
	}

	public State getState(Short stateId){
		return iStateService.getState(stateId);
	}
	
	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
	
	public State findStateById(Short stateId){
		return iStateService.getState(stateId);
	}
	
}
