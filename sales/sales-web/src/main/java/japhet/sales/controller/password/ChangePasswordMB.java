package japhet.sales.controller.password;

import static japhet.sales.mailing.MailingParameters.*;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.AuthConstants;
import japhet.sales.controller.GenericMB;
import japhet.sales.except.InvalidPasswordException;
import japhet.sales.except.UnmatchedPasswordException;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.mailing.ContentTypes;
import japhet.sales.mailing.MailingTemplates;
import japhet.sales.mailing.service.IMailingService;
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@ManagedBean
@RequestScoped
public class ChangePasswordMB extends GenericMB 
	implements AuthConstants {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 832180123336647643L;
	
	//View attributes
	private String currentPassword;
	private String oldPassword;
	private String newPassword;
	private String confirmedPassword;
	
	//Logic attribute
	private User user;

	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IUserService userService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	@EJB
	private IMailingService mailingService;
	
	/**
	 * Initializes the values that are required 
	 * for this component.
	 */
	@PostConstruct
	private void init() {
		try {
			logger.info("initializing ChangePasswordMB...");
			//Get logger user
			this.user = getLoggedUser();
			this.currentPassword = user.getPassword();
		} catch (Exception e) {
			final String ERROR_MSG = "An error has occurred while initializing ChangePasswordMB.";
			logger.error(ERROR_MSG, e);
			showStartupMbExceptionMessage();
		}
	}
	
	/**
	 * This method handles the action request 
	 * to update the user password.
	 */
	public void changeUserPassword() {
		final long UID = this.user.getUserId();
		final String INFO_MSG = String.format("Updating the password from the user: %d...", UID);
		try {
			logger.info(INFO_MSG);
			Map<String, Object> params = new HashMap<>();
			//Set parameters
			params.put(CURRENT_PASSWORD, this.currentPassword);
			params.put(CONFIRMED_PASSWORD, this.confirmedPassword);
			params.put(PASSW, this.newPassword);
			params.put(OLD_PASSWORD, this.oldPassword);
			params.put(USER_ID, this.user.getUserId());
			//Service request
			userService.updateUserPassword(params);
			//Set mailing parameters
			params.clear();
			params.put(NAME, this.user.getName());
			mailingService.sendMessage(MailingTemplates.PASSWORD_UPDATED.getSubject(), 
					this.user.getEmail(), 
					MailingTemplates.PASSWORD_UPDATED, 
					ContentTypes.TEXT_HTML, 
					params);
			final String SUCCESS_MSG = internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getPASSWORD_UPDATE_SUCCESSFUL());
			showInfoMessage(SUCCESS_MSG, "");
			this.clear();
		} catch (UnmatchedPasswordException e) {
			final String ERROR_MSG = String
					.format("The new user: %d password and its confirmation doesn't match.", UID);
			logger.error(ERROR_MSG, e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getUNMATCHED_PASSWORDS()), "");
		} catch (InvalidPasswordException e) {
			final String ERROR_MSG = String
					.format("The current user: %d password is not valid.", UID);
			logger.error(ERROR_MSG, e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getINVALID_PASSWORD_ONCHANGE()), "");
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while updating the user: %d password.", UID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}

	public void clear() {
		this.oldPassword = null;
		this.confirmedPassword = null;
		this.currentPassword = null;
	}
	
	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmedPassword
	 */
	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	/**
	 * @param confirmedPassword the confirmedPassword to set
	 */
	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
	
	/**
	 * @return Max password length required.
	 */
	public short getMAX_PASSWORD_LENGTH() {
		return MAX_PASSWORD_LENGTH;
	}
	
	/**
	 * @return Min password length required.
	 */
	public short getMIN_PASSWORD_LENGTH() {
		return MIN_PASSWORD_LENGTH;
	}
}
