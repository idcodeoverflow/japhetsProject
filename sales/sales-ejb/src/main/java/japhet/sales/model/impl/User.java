package japhet.sales.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import japhet.sales.catalogs.Roles;
import japhet.sales.catalogs.Statuses;
import japhet.sales.data.QueryNames;
import japhet.sales.data.QueryParameters;
import japhet.sales.data.StoredProcedureNames;
import japhet.sales.data.StoredProcedureParameters;
import japhet.sales.model.IEntity;
import japhet.sales.util.Encryption;

@Cacheable(value = true)
@Entity
@Table(name = "TB_USER")
@NamedQueries(value = {
		@NamedQuery(name = QueryNames.EXISTS_USER, 
				query = "SELECT u FROM User u WHERE u.username = :" + QueryParameters.USERNAME + " AND u.passw = :" + QueryParameters.PASSW + " AND u.status.statusId = :" + QueryParameters.STATUS + ""),
		@NamedQuery(name = QueryNames.GET_USER_BY_EMAIL,
				query = "SELECT u FROM User u WHERE u.username = :" + QueryParameters.USERNAME),
		@NamedQuery(name = QueryNames.GET_USER_BY_HASH_KEY,
				query = "SELECT u FROM User u WHERE u.hashKey =:" + QueryParameters.HASH_KEY),
		@NamedQuery(name = QueryNames.UPDATE_USER_PASSWORD,
				query = "UPDATE User u SET u.passw = :" + QueryParameters.PASSW + " WHERE u.userId = :" + QueryParameters.USER_ID + " AND u.passw = :" + QueryParameters.OLD_PASSWORD)
})
@NamedStoredProcedureQueries(value = {
		@NamedStoredProcedureQuery(name = StoredProcedureNames.CHANGE_USER_CATEGORIES_NAME,
				procedureName = StoredProcedureNames.CHANGE_USER_CATEGORIES,
				parameters = {
						@StoredProcedureParameter(name = StoredProcedureParameters.P_USER_ID,
								type = Long.class, 
								mode = ParameterMode.IN),
						@StoredProcedureParameter(name = StoredProcedureParameters.P_CATEGORIES_LIST,
								type = String.class, 
								mode = ParameterMode.IN)
				}
		)
})
public class User implements IEntity, UserDetails {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -6166291428613566595L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "CURP")
	private String curp;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSW")
	private String passw;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	@Column(name = "AGE")
	private Short age;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID")
	private Status status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SIGN_UP_DATE")
	private Date signUpDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModified;
	
	@JoinColumn(name = "CITY_ID")
	@ManyToOne
	private City city;
	
	@Column(name = "HASH_KEY")
	private String hashKey;

	@ManyToMany
	@JoinTable(
			name = "TB_SNETWORK_USER",
			joinColumns = @JoinColumn(name = "USER_ID", 
							referencedColumnName = "USER_ID",
							insertable = true),
			inverseJoinColumns = @JoinColumn(name = "SOCIAL_NETWORK_ID", 
							referencedColumnName = "SOCIAL_NETWORK_ID",
							insertable = true))
	private List<SocialNetwork> socialNetwork;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "TB_USER_CATEGORIES",
			joinColumns = @JoinColumn(name = "USER_ID", 
							referencedColumnName = "USER_ID",
							insertable = true),
			inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID",
							referencedColumnName = "CATEGORY_ID",
							insertable = true))
	private List<Category> categories;
	
	@Column(name = "VALIDATED_ACCOUNT")
	private Boolean validatedAccount;
	
	public User() {
		this.validatedAccount = false;
		//Add the default value for the role
		this.role = new Role();
		this.role.setRoleId(Roles.USER.getId());
		//Add the default value for the status
		this.status = new Status();
		this.status.setStatusId(Statuses.DISABLED.getId());
		//Instantiate a City
		this.city = new City();
		//Initialize dates
		this.lastModified = new Date();
		this.signUpDate = new Date();
	}

	public User(Long userId, String name, String lastName, String curp,
			String email, String username, String passw, Role role,
			Short age, Status status, Date signUpDate, Date lastModified,
			City city, List<SocialNetwork> socialNetwork, 
			List<Category> categories, Boolean validatedAccount,
			boolean validationEmailSent, String hashKey) {
		super();
		this.userId = userId;
		this.name = name;
		this.lastName = lastName;
		this.curp = curp;
		this.email = email;
		this.username = username;
		this.passw = passw;
		this.role = role;
		this.age = age;
		this.status = status;
		this.signUpDate = signUpDate;
		this.lastModified = lastModified;
		this.city = city;
		this.socialNetwork = socialNetwork;
		this.categories = categories;
		this.setValidatedAccount(validatedAccount);
		this.hashKey = hashKey;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		this.email = email;
		this.hashKey = Encryption.SHA256(email);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) throws Exception {
		this.passw = Encryption.SHA256(passw);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<SocialNetwork> getSocialNetwork() {
		return socialNetwork;
	}

	public void setSocialNetwork(List<SocialNetwork> socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Boolean getValidatedAccount() {
		return validatedAccount;
	}

	public void setValidatedAccount(Boolean validatedAccount) {
		this.validatedAccount = validatedAccount;
	}
	
	/**
	 * @return the hashKey
	 */
	public String getHashKey() {
		return hashKey;
	}

	/**
	 * @param hashKey the hashKey to set
	 */
	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.passw;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.status.getStatusId() == Statuses.ACTIVE.getId();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.status.getStatusId() != Statuses.BLOCKED.getId();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.status.getStatusId() != Statuses.DISABLED.getId();
	}

	@Override
	public boolean isEnabled() {
		return this.status.getStatusId() == Statuses.ACTIVE.getId();
	}
}
