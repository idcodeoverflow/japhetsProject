package japhet.sales.controller.manager;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Roles;
import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.User;
import japhet.sales.model.impl.UserInformation;
import japhet.sales.service.IUserInformationService;

@ManagedBean
@ViewScoped
public class UserInformationMB extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 7203831713114939134L;

	@Inject
	private Logger logger;
	
	//EJBs
	@EJB
	private IUserInformationService userInformationService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	//Logic attributes
	private User user;
	
	//View attributes
	private UserInformation userInformation;
	private Date startTime;
	private Date endTime;
	
	//View constants
	private static final String MESSAGES_ID = "userInformationMessages";
	
	/**
	 * Initializes the required elements for this component.
	 */
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing UserInformationMB...");
			this.user = getLoggedUser();
			final Long USR_ID = user.getUserId();
			//Obtain the UserInformation for the current User
			this.userInformation = userInformationService.getUserInformation(USR_ID);
			if(this.userInformation == null) {
				this.userInformation = new UserInformation();
				this.userInformation.setUserId(USR_ID);
			}
			//Set start and end time
			if(userInformation != null) {
				if(userInformation.getContactSchdStartTime() != null) {
					this.startTime = new Date();
					this.startTime.setTime(userInformation.getContactSchdStartTime().getTime());
				}
				if(userInformation.getContactSchdEndTime() != null) {
					this.endTime = new Date();
					this.endTime.setTime(userInformation.getContactSchdEndTime().getTime());
				}
			}
			//Show warning user pending information message
			if(getLoggedUser().getRole().getRoleId() == Roles.USER.getId()) {
				if(userInformation == null || !userInformation.validUserInformation()) {
					showPendingUserInformationMessage();
					//Update messages
					getRequestContext().update(MESSAGES_ID);
				}
			}
		} catch (Exception e) {
			logger.error("An error has occurred while initializing the UserInformationMB.", e);
			showStartupMbExceptionMessage();
		}
	}
	
	/**
	 * Sends the request to the service layer to 
	 * persist the UserInformation into the DB.
	 */
	public void saveUserInformation() {
		final long USR_ID = ((this.user != null 
				&& this.user.getUserId() != null) ? this.user.getUserId() : -1L);
		final String INFO_MSG = String
				.format("Saving UserInformation from the User: %d...", USR_ID);
		try {
			logger.info(INFO_MSG);
			//Cast values from the Primefaces calendar
			if(startTime != null) {
				userInformation.setContactSchdStartTime(startTime);
			}
			if(endTime != null) {
				userInformation.setContactSchdEndTime(endTime);
			}
			//Persist User Information
			userInformationService.updateOrInsertUserInformation(userInformation);
			showSuccessMessage();
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while saving UserInformation from the User: %d...", USR_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}

	/**
	 * Shows a User Message to notify that the operation was successful.
	 * @throws Exception
	 */
	private void showSuccessMessage() throws Exception {
		final String SUCCESS_MSG = internationalizationService
				.getI18NMessage(CURRENT_LOCALE, getUSER_INFORMATION_UPDATED());
		showInfoMessage(SUCCESS_MSG, "");
	}
	
	/**
	 * Shows a User Message to notify that some user information is missing.
	 * @throws Exception
	 */
	private void showPendingUserInformationMessage() throws Exception {
		final String WARN_MSG = internationalizationService
				.getI18NMessage(CURRENT_LOCALE, getPENDING_USER_INFORMATION());
		showWarnMessage(WARN_MSG, "");
	}
	
	/**
	 * @return the userInformation
	 */
	public UserInformation getUserInformation() {
		return userInformation;
	}

	/**
	 * @param userInformation the userInformation to set
	 */
	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the messagesId
	 */
	public synchronized String getMESSAGES_ID() {
		return MESSAGES_ID;
	}
}
