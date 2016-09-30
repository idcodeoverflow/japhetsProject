package japhet.sales.controller.registration;

import static japhet.sales.catalogs.Roles.*;
import static japhet.sales.catalogs.Status.*;
import static japhet.sales.catalogs.SocialNetworks.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.City;
import japhet.sales.model.impl.Role;
import japhet.sales.model.impl.SocialNetwork;
import japhet.sales.model.impl.State;
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
	private Short stateId;
	private Short cityId;
	private Short roleId;
	
	//Logic attributes
	private User user;
	private Role role;
	private Status status;
	private City city;
	private State selectedState;
	
	@PostConstruct
	private void init(){
		user = new User();
		role = new Role();
		status = new Status();
		city = new City();
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

	public Short getCityId() {
		return cityId;
	}

	public void setCityId(Short cityId) {
		this.cityId = cityId;
	}

	public Short getStateId() {
		return stateId;
	}

	public void setStateId(Short stateId) {
		this.stateId = stateId;
	}
	
	public Short getRoleId() {
		return roleId;
	}

	public void setRoleId(Short roleId) {
		this.roleId = roleId;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(State selectedState) {
		this.selectedState = selectedState;
	}

	public void signUp() {
		//TODO: complete role and status logic
		logger.info("Persisting user into the DB...");
		try {
			createUser(user);
		} catch (Exception e) {
			logger.severe("Error trying to persist user into the DB." + e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	public void signUpWithFacebook() {
		//TODO: complete role and status logic
		logger.info("Persisting FB user into the DB...");
		List<SocialNetwork> socialNetworks = new ArrayList<>();
		try {
			SocialNetwork facebook = new SocialNetwork();
			facebook.setSocialNetworkId(FACEBOOK.getId());
			socialNetworks.add(facebook);
			user.setSocialNetwork(socialNetworks);
			createUser(user);
		} catch (Exception e) {
			logger.severe("Error trying to persist FB user into the DB." + e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	private void createUser(User user) throws Exception {
		role.setRoleId(((roleId == null ) ? USER.getId() : this.roleId));
		status.setStatusId(ACTIVE.getId());
		city.setCityId(cityId);
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
		user.setCity(city);
		userService.insertUser(user);
	}
	
}
