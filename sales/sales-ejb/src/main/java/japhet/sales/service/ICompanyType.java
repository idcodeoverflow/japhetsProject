package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.CompanyType;

@Local
public interface ICompanyType extends Serializable {
	
	public boolean insertCompanyType(CompanyType companyType) 
			throws BusinessServiceException;
	
	public boolean updateCompanyType(CompanyType companyType) 
			throws BusinessServiceException;
	
	public boolean deleteCompanyType(CompanyType companyType)
			throws BusinessServiceException;
	
	public CompanyType getCompanyType(Short companyTypeId)
			throws BusinessServiceException;

	public List<CompanyType> getAllCompanyTypes()
			throws BusinessServiceException;
}
