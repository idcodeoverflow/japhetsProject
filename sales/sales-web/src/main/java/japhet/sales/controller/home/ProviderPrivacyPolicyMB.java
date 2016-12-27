package japhet.sales.controller.home;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.User;

@ManagedBean
@RequestScoped
public class ProviderPrivacyPolicyMB 
	extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -7273136990148816209L;
	
	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getCompanyName() {
		String companyName = "";
		if(company != null) {
			User user = company.getUser();
			if(user != null) {
				//Append first name if exists
				if(user.getName() != null 
						&& !"".equals(user.getName())){
					companyName = String.format("%s", user.getName());
				}
				//Append last name if exits
				if(user.getLastName() != null 
						&& !"".equals(user.getLastName())){
					companyName = String.format("%s %s", user.getName(), user.getLastName());
				}
			}
		}
		logger.info(companyName);
		return companyName;
	}
}
