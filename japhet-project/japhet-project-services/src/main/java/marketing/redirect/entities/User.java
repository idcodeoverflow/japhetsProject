package marketing.redirect.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_USER")
public class User implements Serializable {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -6166291428613566595L;

	@Id
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
	
	@Column(name = "STATUS_ID")
	private Short statusId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SIGN_UP_DATE")
	private Timestamp signUpDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED")
	private Timestamp lastModified;
	
	public User() {}

	public User(Long userId, String name, String lastName, String email, String username, String passw, Short role,
			Short age, Short statusId, Timestamp signUpDate, Timestamp lastModified) {
		super();
		this.userId = userId;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.passw = passw;
		this.role = role;
		this.age = age;
		this.statusId = statusId;
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

	public Short getStatusId() {
		return statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	public Timestamp getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Timestamp signUpDate) {
		this.signUpDate = signUpDate;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

}
