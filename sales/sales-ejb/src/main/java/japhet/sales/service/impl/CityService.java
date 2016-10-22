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
		logger.info("Obtaining all cities...");
		List<City> cities = null;
		try {
			cities = cityDAO.getAllCities();
		} catch (Exception e) {
			final String errorMsg = "Error obtaining all the cities.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return cities;
	}

	@Override
	public City getCity(Short cityId)   
			throws BusinessServiceException {
		logger.info("Obtaining city: " + cityId);
		City city = null;
		try {
			city = cityDAO.select(cityId);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining city: " + cityId;
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return city;
	}
}
