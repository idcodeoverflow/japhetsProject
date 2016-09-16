package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.Company;

@Stateless
public class CompanyDAO extends GenericDAO<Company, Long> {

	public CompanyDAO() {
		super(Company.class, Long.class);
	}

}
