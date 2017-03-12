package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.City;

import org.apache.log4j.Logger;

@Stateless
public class CityDAO extends GenericDAO<City, Short> {

	@Inject
	private Logger logger;
	
	public CityDAO(){
		super(City.class, Short.class);
	}
	
	public List<City> getAllCities() 
			throws DataLayerException {
		List<City> cities = null;
		try {
			logger.info("Obtaining all cities...");
			cities = executeQuery(QueryNames.GET_ALL_CITIES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error trying to obtain all cities.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return cities;
	}
	
}
