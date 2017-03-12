package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.State;

import org.apache.log4j.Logger;

@Stateless
public class StateDAO extends GenericDAO<State, Short> {

	@Inject
	private Logger logger;
	
	public StateDAO() {
		super(State.class, Short.class);
	}
	
	public List<State> getAllStates() throws DataLayerException {
		List<State> states = null;
		try {
			logger.info("Obtaining all the States...");
			states = executeQuery(QueryNames.GET_ALL_STATES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain all the states.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return states;
	}
	
}
