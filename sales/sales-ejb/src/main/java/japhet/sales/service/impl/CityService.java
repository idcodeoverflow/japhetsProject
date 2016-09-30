package japhet.sales.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.CityDAO;
import japhet.sales.model.impl.City;
import japhet.sales.service.ICityService;

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
	public List<City> getAllCities() {
		logger.info("Obtaining all cities...");
		return cityDAO.getAllCities();
	}

	@Override
	public City getCity(Short cityId) {
		logger.info("Obtaining city: " + cityId);
		City city = null;
		try {
			city = cityDAO.select(cityId);
		} catch (Exception e) {
			logger.severe("Error while obtaining city: " + cityId 
					+ "\n" + e.getStackTrace());
		}
		return city;
	}

}
