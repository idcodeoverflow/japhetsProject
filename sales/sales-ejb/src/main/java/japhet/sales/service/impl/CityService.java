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
		return cityDAO.getAllCities();
	}

	@Override
	public City getCity(Short cityId)   
			throws BusinessServiceException {
		logger.info("Obtaining city: " + cityId);
		City city = null;
		try {
			city = cityDAO.select(cityId);
		} catch (Exception e) {
			logger.fatal("Error while obtaining city: " + cityId, e);
		}
		return city;
	}

}
