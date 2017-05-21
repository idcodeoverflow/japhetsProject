package japhet.sales.controller.registration;

import static japhet.sales.catalogs.SocialNetworks.FACEBOOK;
import static japhet.sales.mailing.MailingParameters.*;
import static japhet.sales.mailing.MailingTemplates.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import japhet.sales.catalogs.Roles;
import japhet.sales.controller.AuthConstants;
import japhet.sales.controller.GenericMB;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.mailing.ContentTypes;
import japhet.sales.mailing.MailingParameters;
import japhet.sales.mailing.service.IMailingService;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.SocialNetwork;
import japhet.sales.model.impl.State;
import japhet.sales.model.impl.User;
import japhet.sales.service.ICompanyService;
import japhet.sales.service.IRandomPasswordService;
import japhet.sales.service.IUserService;
import japhet.sales.service.IUtilService;

@ManagedBean
@ViewScoped
public class RegistrationMB extends GenericMB 
	implements AuthConstants {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -799929358184487082L;
	
	private static final long MAX_MEDIA_SIZE = 2500000L;
	private static final short MAX_RFC_LENGTH = 13;
	private static final short MAX_CURP_LENGTH = 18;
	private static final short MIN_RFC_LENGTH = 12;
	private static final short MIN_CURP_LENGTH = 18;
	private static final short MAX_AGE_LENGTH = 3;
	private static final short MIN_AGE_LENGTH = 1;
	private static final short MIN_NDAYS_TO_ALLOW_PAYBACK = 1;
	private static final short MAX_NDAYS_TO_ALLOW_PAYBACK = 3;
	
	@Inject
	private Logger logger;

	//EJB's
	@EJB
	private IUserService userService;
	
	@EJB
	private IUtilService utilService;
	
	@EJB
	private ICompanyService companyService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	@EJB
	private IMailingService mailingService;
	
	@EJB
	private IRandomPasswordService randomPasswordService;
	
	//View properties
	private String password;
	private String confirmPassword;
	private byte[] imageBytes;
	
	//Logic attributes
	private User user;
	private Company company;
	private State selectedState;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing RegistrationMB...");
			clear();
		} catch (Exception e) {
			logger.error("An error has ocurred while initializing RegistrationMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
        try {
        	logger.info("Uploading Company Image...");
			imageBytes = utilService.getBiteArrayFromStream(
					event.getFile().getInputstream());
			logger.info("Company Image Uploaded succesfuly!");
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getIMAGE_READY()), "");
		} catch (Exception e) {
			logger.error("Error while uploading Company Image.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getIMAGE_UPLOAD_ERROR()), 
					event.getFile().getFileName());
		}
    }
	
	public void signUp() {
		logger.info("Signing Up user...");
		String contextPath = null;
		Map<String, Object> params = new HashMap<>();
		try {
			if(isAdministratorRole() || isCompanyRole()) {
				//Create random password
				password = randomPasswordService.generateRandomPassword((short)10);
				confirmPassword = password;
			}
			createUser(user);
			//Build Context Path
			contextPath = new URL(getExternalContext().getRequestScheme(), 
					getExternalContext().getRequestServerName(),  
					getExternalContext().getRequestServerPort(),
					REST_VALIDATOR_VALIDATE_URL).toString();
			//Persist entities
			if(isUserRole()) {
				//Set mailing parameters
				params.put(NAME, user.getName() + " " + user.getLastName());
				params.put(REQUEST_CONTEXT, contextPath);
				params.put(USER_HASH_KEY, user.getHashKey());
				//Persist user entity
				userService.insertUser(user);
				//Send email
				mailingService.sendMessage(WELCOME_MAIL.getSubject(), user.getUsername(),
						WELCOME_MAIL, ContentTypes.TEXT_HTML, params);
				clear();
				redirect(SIGN_IN_URL);
			} else if (isAdministratorRole()) {
				//Set mailing parameters
				params.put(NAME, user.getName());
				params.put(REQUEST_CONTEXT, contextPath);
				params.put(USER_HASH_KEY, user.getHashKey());
				params.put(MailingParameters.USERNAME, user.getUsername());
				params.put(PASSWORD, password);
				//Persist user entity
				userService.insertUser(user);
				//Send email
				mailingService.sendMessage(WELCOME_COMPANY_MAIL.getSubject(), user.getUsername(),
						WELCOME_COMPANY_MAIL, ContentTypes.TEXT_HTML, params);
				//If everything is good at this points notice the user: "Success!"
				showInfoMessage(internationalizationService
						.getI18NMessage(CURRENT_LOCALE, getUSER_REGISTERED()), "");
				clear();
			} else if(isCompanyRole()) {
				//Set age as 0
				user.setAge((short) 0);
				//Fill company object
				company.setImage(imageBytes);
				company.setUser(user);
				//Set mailing parameters
				params.put(NAME, user.getName());
				params.put(REQUEST_CONTEXT, contextPath);
				params.put(USER_HASH_KEY, user.getHashKey());
				params.put(MailingParameters.USERNAME, user.getUsername());
				params.put(PASSWORD, password);
				//Persist company entity
				companyService.insertCompany(company, user);
				//Send email
				mailingService.sendMessage(WELCOME_COMPANY_MAIL.getSubject(), user.getUsername(),
						WELCOME_COMPANY_MAIL, ContentTypes.TEXT_HTML, params);
				//If everything is good at this points notice the user: "Success!"
				showInfoMessage(internationalizationService
						.getI18NMessage(CURRENT_LOCALE, getUSER_REGISTERED()), "");
				clear();
			}
		} catch (InvalidPasswordException e) {
			logger.fatal("The password is invalid.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getINVALID_PASSWORD_ERROR()), "");
		} catch (Exception e) {
			logger.fatal("Error trying to persist user into the DB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSIGN_UP_ERROR()), "");
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
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSIGN_UP_ERROR()), "");
		}
	}
	
	private void createUser(User user) throws Exception {
		userService.validatePasswords(password, confirmPassword);
		user.setPassw(password);
		//Replace with username field if exists
		user.setUsername(user.getEmail());
	}
	
	private void clear() {
		this.user = new User();
		//If the user is not an administrator set the default role as USER
		if(getLoggedUser() != null) {
			this.user.getRole().setRoleId(null);
		} else {
			this.user.getRole().setRoleId(Roles.USER.getId());
		}
		this.company = new Company();
		this.imageBytes = null;
		this.confirmPassword = null;
		this.password = null;
		this.selectedState = new State();
	}
	
	public boolean isAdministratorRole() {
		return user!= null && user.getRole() != null &&
				user.getRole().getRoleId() == Roles.ADMINISTRATOR.getId();
	}
	
	public boolean isCompanyRole() {
		return user!= null && user.getRole() != null &&
			user.getRole().getRoleId() == Roles.COMPANY.getId();
	}
	
	public boolean isUserRole() {
		return user!= null && user.getRole() != null &&
			user.getRole().getRoleId() == Roles.USER.getId();
	}

	public void updateRole(Short roleId) {
		this.user.getRole().setRoleId(roleId);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(State selectedState) {
		this.selectedState = selectedState;
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

	public short getMIN_PASSWORD_LENGTH() {
		return MIN_PASSWORD_LENGTH;
	}
	
	public short getMAX_PASSWORD_LENGTH() {
		return MAX_PASSWORD_LENGTH;
	}
	
	public short getMAX_EMAIL_LENGTH() {
		return MAX_EMAIL_LENGTH;
	}

	public long getMAX_MEDIA_SIZE() {
		return MAX_MEDIA_SIZE;
	}

	public short getMAX_RFC_LENGTH() {
		return MAX_RFC_LENGTH;
	}

	public short getMAX_CURP_LENGTH() {
		return MAX_CURP_LENGTH;
	}

	public short getMIN_RFC_LENGTH() {
		return MIN_RFC_LENGTH;
	}

	public short getMIN_CURP_LENGTH() {
		return MIN_CURP_LENGTH;
	}

	public short getMAX_AGE_LENGTH() {
		return MAX_AGE_LENGTH;
	}

	public short getMIN_AGE_LENGTH() {
		return MIN_AGE_LENGTH;
	}

	public short getMIN_NDAYS_TO_ALLOW_PAYBACK() {
		return MIN_NDAYS_TO_ALLOW_PAYBACK;
	}

	public short getMAX_NDAYS_TO_ALLOW_PAYBACK() {
		return MAX_NDAYS_TO_ALLOW_PAYBACK;
	}
}
