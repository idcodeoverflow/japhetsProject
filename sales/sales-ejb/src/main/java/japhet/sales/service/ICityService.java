package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.City;

@Local
public interface ICityService extends Serializable {

	public List<City> getAllCities() 
			throws BusinessServiceException;
	
	public City getCity(Short cityId) 
			throws BusinessServiceException;
		
}
