package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Company;

@Local
public interface ICompanyService extends Serializable {

	public List<Company> getAllAvailableCompanies(List<Short> idStatuses) 
			throws BusinessServiceException;
	
	public List<Company> getAllCompanies(List<Short> idStatuses) 
			throws BusinessServiceException;
	
	public Company selectCompany(Long companyId) 
			throws BusinessServiceException;
	
	public boolean updateCompany(Company companyId) 
			throws BusinessServiceException;
	
	public boolean deleteCompany(Company companyId) 
			throws BusinessServiceException;
	
	public boolean insertCompany(Company companyId) 
			throws BusinessServiceException;
}
