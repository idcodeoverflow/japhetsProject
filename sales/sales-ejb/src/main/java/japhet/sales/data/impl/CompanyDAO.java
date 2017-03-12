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
		List<Company> companies = null;
		Map<String, Object> params = new HashMap<>();
		try {
			logger.info("Getting all available companies...");
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
		List<Company> companies = null;
		Map<String, Object> params = new HashMap<>();
		try {
			logger.info(LOGGER_MSG);
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
		List<Company> companies = null;
		try {
			logger.info("Getting all companies...");
			companies = executeQuery(GET_ALL_COMPANIES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all companies...";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return companies;
	}
	
	public Company getCompanyByUserId(Map<String, Object> parameters) 
			throws DataLayerException {
		List<Company> companies = null;
		Company company = null;
		try {
			logger.info("Getting Company by userId from the DB...");
			companies = executeQuery(GET_COMPANY_BY_USER_ID, parameters);
			if(companies != null && !companies.isEmpty()) {
				company = companies.get(0);
			}
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while getting a Company by userId %s", 
					parameters.get(USER_ID));
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return company;
	}
}
