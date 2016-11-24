package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Company;
import japhet.sales.service.ICompanyService;

@LocalBean
@Stateless
public class CompanyService implements ICompanyService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 6344195007440839444L;

	@Inject
	private Logger logger;
	
	@EJB
	private ICompanyService companyService;
	
	@Override
	public List<Company> getAllAvailableCompanies(List<Short> idStatuses) 
			throws BusinessServiceException {
		logger.info("Getting all available companies...");
		return null;
	}

	@Override
	public List<Company> getAllCompanies(List<Short> idStatuses) 
			throws BusinessServiceException {
		logger.info("Getting all companies...");
		return null;
	}

	@Override
	public Company selectCompany(Long companyId) 
			throws BusinessServiceException {
		logger.info("Getting company: " + companyId + "...");
		return null;
	}

	@Override
	public boolean updateCompany(Company companyId) 
			throws BusinessServiceException {
		logger.info("Updating company: " + companyId + "...");
		return false;
	}

	@Override
	public boolean deleteCompany(Company companyId) 
			throws BusinessServiceException {
		logger.info("Deleting company: " + companyId + "...");
		return false;
	}

	@Override
	public boolean insertCompany(Company companyId) 
			throws BusinessServiceException {
		logger.info("Inserting company: " + companyId + "...");
		return false;
	}

}
