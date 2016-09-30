package japhet.sales.data.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.data.QueryNames;
import japhet.sales.model.impl.City;

@Stateless
public class CityDAO extends GenericDAO<City, Short> {

	@Inject
	private Logger logger;
	
	public CityDAO(){
		super(City.class, Short.class);
	}
	
	public List<City> getAllCities() {
		List<City> cities = null;
		try {
			logger.info("Obtaining all cities...");
			cities = executeQuery(QueryNames.GET_ALL_CITIES, null);
		} catch (Exception e) {
			logger.severe("Error trying to obtain all cities." + 
					e.getStackTrace());
		}
		return cities;
	}
	
}
