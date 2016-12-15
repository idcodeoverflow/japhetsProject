package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.User;

@Local
public interface ICompanyService extends Serializable {

	public List<Company> getAllAvailableCompanies() 
			throws BusinessServiceException;
	
	public List<Company> getAllCompanies() 
			throws BusinessServiceException;
	
	public Company selectCompany(Long companyId) 
			throws BusinessServiceException;
	
	public boolean updateCompany(Company companyId) 
			throws BusinessServiceException;
	
	public boolean deleteCompany(Company companyId) 
			throws BusinessServiceException;
	
	public boolean insertCompany(Company company) 
			throws BusinessServiceException;
	
	public boolean insertCompany(Company company, User user)
			throws BusinessServiceException, Exception;
}
