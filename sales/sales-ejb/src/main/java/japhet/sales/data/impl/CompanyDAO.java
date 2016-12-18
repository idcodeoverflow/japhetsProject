package japhet.sales.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.Company;

@Stateless
public class CompanyDAO extends GenericDAO<Company, Long> {

	@Inject
	private Logger logger;
	
	//Query parameters
	final String VALID_STATUSES = "validStatuses";
	final String COMPANY_TYPE_ID = "companyTypeId";
	
	public CompanyDAO() {
		super(Company.class, Long.class);
	}
	
	public List<Company> getAllAvailableCompanies(List<Short> idStatuses) 
		throws DataLayerException {
		logger.info("Getting all available companies...");
		List<Company> companies = null;
		Map<String, Object> params = new HashMap<>();
		try {
			params.put(VALID_STATUSES, idStatuses);
			companies = executeQuery(GET_ALL_AVAILABLE_COMPANIES, params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all available companies...";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return companies;
	}
	
	public List<Company> getAllAvailableCompaniesOfType(List<Short> idStatuses, Short companyTypeId) 
		throws DataLayerException {
		final String LOGGER_MSG = String.format("Getting all available companies of type: %d...", 
				companyTypeId);
		logger.info(LOGGER_MSG);
		List<Company> companies = null;
		Map<String, Object> params = new HashMap<>();
		try {
			params.put(VALID_STATUSES, idStatuses);
			params.put(COMPANY_TYPE_ID, companyTypeId);
			companies = executeQuery(GET_ALL_AVAILABLE_COMPANIES_OF_TYPE, params);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while getting all available companies of type: %d.", 
					companyTypeId);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return companies;
	}
	
	public List<Company> getAllCompanies() 
		throws DataLayerException {
		logger.info("Getting all companies...");
		List<Company> companies = null;
		try {
			companies = executeQuery(GET_ALL_COMPANIES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all companies...";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return companies;
	}
	
}
