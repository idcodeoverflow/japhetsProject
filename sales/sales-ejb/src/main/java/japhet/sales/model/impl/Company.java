package japhet.sales.model.impl;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import japhet.sales.model.IEntity;

@Entity
@Cacheable(value = true)
@Table(name = "TB_COMPANY")
public class Company implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -7669292815087490687L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPANY_ID")
	private Long companyId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	public Company() {}

	public Company(Long companyId, User user) {
		super();
		this.companyId = companyId;
		this.user = user;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
