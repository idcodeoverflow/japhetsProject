package marketing.redirect.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_COMPANY")
public class Company implements Serializable {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -7669292815087490687L;

	@Id
	@Column(name = "COMPANY_ID")
	private Long companyId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	public Company() {}

	public Company(Long companyId, Long userId) {
		super();
		this.companyId = companyId;
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
