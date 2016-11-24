package japhet.sales.model.impl;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
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
	
	@Column(name = "URL")
	private String url;
	
	@Lob
	@Column(name = "IMAGE")
	private byte[] image;
	
	@Column(name = "PRIVACY_POLICY")
	private String privacyPolicy;
	
	public Company() {}

	public Company(Long companyId, User user, String url, 
			byte[] image, String privacyPolicy) {
		super();
		this.companyId = companyId;
		this.user = user;
		this.url = url;
		this.image = image;
		this.privacyPolicy = privacyPolicy;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getPrivacyPolicy() {
		return privacyPolicy;
	}

	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}
}
