package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.CompanyType;

@Stateless
public class CompanyTypeDAO extends GenericDAO<CompanyType, Short> {

	@Inject
	private Logger logger;
	
	public CompanyTypeDAO() {
		super(CompanyType.class, Short.class);
	}
	
	public List<CompanyType> getAllCompanyTypes() throws DataLayerException {
		List<CompanyType> companyTypes = null;
		try {
			logger.info("Getting all the Company Types from the DB...");
			companyTypes = executeQuery(GET_ALL_COMPANY_TYPES, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all the Company Types from the DB.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return companyTypes;
	}
	
}
