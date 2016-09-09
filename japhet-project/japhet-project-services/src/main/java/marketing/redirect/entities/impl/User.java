package marketing.redirect.entities.impl;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import marketing.redirect.entities.IEntity;

@Entity
@Cacheable(value = true)
@Table(name = "TB_USER")
public class User implements IEntity {

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
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSW")
	private String passw;
	
	@Column(name = "ROLE")
	private Short role;
	
	@Column(name = "AGE")
	private Short age;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATUS_ID")
	private Status status;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGN_UP_DATE")
	private Date signUpDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_MODIFIED")
	private Date lastModified;
	
	public User() {}

	public User(Long userId, String name, String lastName, 
			String email, String username, String passw, Short role,
			Short age, Status status, Date signUpDate, Date lastModified) {
		super();
		this.userId = userId;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.passw = passw;
		this.role = role;
		this.age = age;
		this.status = status;
		this.signUpDate = signUpDate;
		this.lastModified = lastModified;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public Short getRole() {
		return role;
	}

	public void setRole(Short role) {
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

}
