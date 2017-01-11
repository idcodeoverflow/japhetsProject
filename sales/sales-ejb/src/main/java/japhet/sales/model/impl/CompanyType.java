package japhet.sales.model.impl;

import static japhet.sales.data.QueryNames.*;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_COMPANY_TYPE")
@NamedQueries(value = {
		@NamedQuery(name = GET_ALL_COMPANY_TYPES,
				query = "SELECT ct FROM CompanyType ct")
})
public class CompanyType implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -4209008092708191073L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPANY_TYPE_ID")
	private Short companyTypeId;
	
	@Column(name = "NAME")
	private String name;
	
	public CompanyType(){}

	public CompanyType(Short companyTypeId, String name) {
		super();
		this.companyTypeId = companyTypeId;
		this.name = name;
	}
	
	public Short getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(Short companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
