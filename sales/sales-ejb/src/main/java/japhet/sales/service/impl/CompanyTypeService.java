package japhet.sales.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.impl.CompanyTypeDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.CompanyType;
import japhet.sales.service.ICompanyTypeService;

@Startup
@Singleton
public class CompanyTypeService implements ICompanyTypeService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5342712694601466967L;

	@Inject
	private Logger logger;
	
	@EJB
	private CompanyTypeDAO companyTypeDAO;
	
	//Cache attributes
	private List<CompanyType> companyTypes;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing CompanyTypeService...");
			this.companyTypes = getAllCompanyTypes();
		} catch (BusinessServiceException e) {
			logger.fatal("Error while initializing CompanyTypeService.", e);
		}
	}
	
	@Override
	public boolean insertCompanyType(CompanyType companyType) 
			throws BusinessServiceException {
		try {
			logger.info(String.format("Inserting Company Type: %s...", 
					companyType.getCompanyTypeId()));
			companyTypeDAO.insert(companyType);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while inserting Company Type: %s...", 
					companyType.getCompanyTypeId());
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean updateCompanyType(CompanyType companyType) 
			throws BusinessServiceException {
		try {
			logger.info(String.format("Updating Company Type: %s...", 
					companyType.getCompanyTypeId()));
			companyTypeDAO.update(companyType);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while updating Company Type: %s...", 
					companyType.getCompanyTypeId());
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public boolean deleteCompanyType(CompanyType companyType) 
			throws BusinessServiceException {
		try {
			logger.info(String.format("Deleting Company Type: %s...", 
					companyType.getCompanyTypeId()));
			companyTypeDAO.delete(companyType);
			return true;
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while deleting Company Type: %s...", 
					companyType.getCompanyTypeId());
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public CompanyType getCompanyType(Short companyTypeId) 
			throws BusinessServiceException {
		CompanyType companyType = null;
		try {
			logger.info(String.format("Getting Company Type: %s...", companyTypeId));
			companyType = companyTypeDAO.select(companyTypeId);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while getting Company Type: %s.", companyTypeId);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return companyType;
	}

	@Override
	public List<CompanyType> getAllCompanyTypes() 
			throws BusinessServiceException {
		this.companyTypes = null;
		try {
			logger.info("Getting all company types...");
			this.companyTypes = companyTypeDAO.getAllCompanyTypes();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all company types.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return this.companyTypes;
	}
	
	@Override
	public List<CompanyType> getCachedCompanyTypes()
			throws BusinessServiceException {
		return this.companyTypes;
	}
}
