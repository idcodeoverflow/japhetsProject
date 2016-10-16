package japhet.sales.controller.registration;

import static japhet.sales.catalogs.SocialNetworks.FACEBOOK;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import japhet.sales.controller.GenericMB;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.model.impl.SocialNetwork;
import japhet.sales.model.impl.State;
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;

import org.apache.log4j.Logger;

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
	private String password;
	private String confirmPassword;
	
	//Logic attributes
	private User user;
	private State selectedState;
	
	@PostConstruct
	private void init(){
		user = new User();
		selectedState = new State();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(State selectedState) {
		this.selectedState = selectedState;
		logger.info("Si entro!!!!!!!" + selectedState.getStateId());
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

	public void signUp() {
		//TODO: complete role and status logic
		logger.info("Persisting user into the DB...");
		try {
			createUser(user);
		} catch (Exception e) {
			logger.fatal("Error trying to persist user into the DB.", e);
			showErrorMessage("An error has ocurred registering the account.", "");
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
			logger.fatal("Error trying to persist FB user into the DB.", e);
			showErrorMessage("An error has ocurred while signing up.", "");
		}
	}
	
	private void createUser(User user) {
		try {
			userService.validatePasswords(password, confirmPassword);
			userService.insertUser(user);
		} catch (InvalidPasswordException e) {
			logger.fatal("The password is invalid.", e);
			showErrorMessage("The password doesn't match or is invalid", "");
		}
	}
	
}
