package japhet.sales.controller.registration;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Role;
import japhet.sales.model.impl.Status;
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;

@ManagedBean
@ViewScoped
public class RegistrationMB extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -799929358184487082L;
	
	@EJB
	private IUserService userService;
	
	@Inject
	private Logger logger;
	
	//View propertiess
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private Short age;
	private String password;
	private String confirmPassword;
	private Integer countryId;
	private Integer stateId;
	
	//Logic attributes
	private User user;
	private Role role;
	private Status status;
	
	@PostConstruct
	public void init(){
		user = new User();
		role = new Role();
		status = new Status();
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
		this.username = email;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
	public void signUp() {
		//TODO: complete role and status logic
		logger.info("Persisting user into the DB...");
		try {
			role.setRoleId((short) 1);
			status.setStatusId((short) 1);
			user.setAge(age);
			user.setEmail(email);
			user.setLastModified(new Date());
			user.setLastName(lastName);
			user.setName(firstName);
			user.setPassw(password);
			user.setRole(role);
			user.setSignUpDate(new Date());
			user.setStatus(status);
			user.setUsername(username);
			userService.insertUser(user);
		} catch (Exception e) {
			logger.severe("Error trying to persist user into the DB." + e.getStackTrace());
			e.printStackTrace();
		}
		
	}
	
}
