package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.CityDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.City;
import japhet.sales.service.ICityService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class CityService implements ICityService {

	@Inject
	private Logger logger;
	
	@EJB
	private CityDAO cityDAO;
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -4659743105602399164L;

	@Override
	public List<City> getAllCities()   
			throws BusinessServiceException {
		List<City> cities = null;
		try {
			logger.info("Obtaining all cities...");
			cities = cityDAO.getAllCities();
		} catch (Exception e) {
			final String ERROR_MSG = "Error obtaining all the cities.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return cities;
	}

	@Override
	public City getCity(Short cityId)   
			throws BusinessServiceException {
		City city = null;
		try {
			logger.info("Obtaining city: " + cityId);
			city = cityDAO.select(cityId);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining city: " + cityId;
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return city;
	}
}
