package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.model.impl.City;

@Local
public interface ICityService extends Serializable {

	public List<City> getAllCities();
	
	public City getCity(Short cityId);
		
}
