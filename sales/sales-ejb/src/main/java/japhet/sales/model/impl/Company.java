package japhet.sales.model.impl;

import static japhet.sales.data.QueryNames.*;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import japhet.sales.model.IEntity;
import japhet.sales.util.StreamUtil;

@Cacheable(value = true)
@Entity
@Table(name = "TB_COMPANY")
@NamedQueries(value = {
		@NamedQuery(name = GET_ALL_AVAILABLE_COMPANIES, 
				query = "SELECT c FROM Company c"),
		@NamedQuery(name = GET_ALL_COMPANIES, 
			query = "SELECT c FROM Company c WHERE c.user.status.statusId IN :validStatuses")
})
public class Company extends StreamUtil 
	implements IEntity {

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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPANY_TYPE_ID")
	private CompanyType companyType;
	
	@ManyToMany
	@JoinTable(name = "TB_COMPANY_CATEGORY",
			joinColumns = @JoinColumn(name = "COMPANY_ID",
					referencedColumnName = "COMPANY_ID",
					insertable = true),
			inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID",
					referencedColumnName = "CATEGORY_ID",
					insertable = true)
	)
	private List<Category> categories;
	
	public Company() {
		super();
		this.companyType = new CompanyType();
	}

	public Company(Long companyId, User user, String url, 
			byte[] image, String privacyPolicy, 
			CompanyType companyType, List<Category> categories) {
		super();
		this.companyId = companyId;
		this.user = user;
		this.url = url;
		this.image = image;
		this.privacyPolicy = privacyPolicy;
		this.companyType = companyType;
		this.categories = categories;
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

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
