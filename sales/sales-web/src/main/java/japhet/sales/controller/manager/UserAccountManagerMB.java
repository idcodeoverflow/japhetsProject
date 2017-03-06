package japhet.sales.controller.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import japhet.sales.catalogs.Statuses;
import japhet.sales.controller.GenericMB;
import japhet.sales.dto.UserBudget;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidBuyProofException;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.PaymentRequest;
import japhet.sales.model.impl.Status;
import japhet.sales.model.impl.User;
import japhet.sales.model.impl.UserProductHistorial;
import japhet.sales.service.IBuyProofService;
import japhet.sales.service.IPaymentRequestService;
import japhet.sales.service.IUserProductHistorialService;
import japhet.sales.service.IUserService;
import japhet.sales.service.IUtilService;

@ManagedBean
@ViewScoped
public class UserAccountManagerMB extends GenericMB {

	/**
	 * Maven generated
	 */
	private static final long serialVersionUID = -5288053365897420779L;
	
	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private IUserService userService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	@EJB
	private IPaymentRequestService paymentRequestService;
	
	@EJB
	private IBuyProofService buyProofService;
	
	@EJB
	private IUserProductHistorialService userProductHistorialService;
	
	@EJB
	private IUtilService utilService;
	
	//Validation properties
	private final int MAX_MEDIA_SIZE = 1000000;
	private final double MIN_PAYMENT_REQUEST = 200.0;
	
	//View properties
	private byte[] buyProofBytes;
	private PaymentRequest paymentRequest;
	private BuyProof buyProof;
	private List<PaymentRequest> paymentRequestHistory;
	private List<BuyProof> buyProofsHistory;
	private List<UserProductHistorial> userProductHistorials;
	private double onwaitAmount;
	private double readyAmount;
	
	//Query parameters
	private Map<String, Object> params;
	private User user;
	
	/**
	 * Initializes the User Account Manager.
	 */
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing user account manager...");
			//Initialize session properties
			params = new HashMap<>();
			user = getLoggedUser();
			params.put(USER_ID, user.getUserId());
			//Initialize elements
			initializeBuyProof();
			initializePaymentRequest();
			//Update elements
			updateBuyProofsListHistory();
			updateUserProductHistorial();
			updatePaymentRequests();
			updateUserBudget();
		} catch (Exception e) {
			logger.error("Error while initializing user account manager.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	/**
	 * Handles the files upload for the buy proofs.
	 */
	public void handleFileUpload(FileUploadEvent event) {
		try {
			buyProofBytes = utilService.getBiteArrayFromStream(
					event.getFile().getInputstream());
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getFILE_READY()), "");
			logger.info("Uploading buy proof file...");
			//Persist into the DB
			buyProof.setTicketImage(buyProofBytes);
			buyProof.setFileName(event.getFile().getFileName());
			buyProof.setContentType(event.getFile().getContentType());
			//Validate and insert
			userProductHistorialService.verifyTotalAmounts(buyProof);
			buyProofService.insertBuyProof(buyProof);
			//Update elements
			updateBuyProofsListHistory();
			updateUserBudget();
			initializeBuyProof();
		} catch (InvalidBuyProofException e) {
			logger.error("The buy proof 'Total Amount' doesn't match the finger print 'Total'.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getBUY_PROOF_INVALID()), 
						event.getFile().getFileName());
		} catch (Exception e) {
			logger.error("There was an error uploading the buy proof file to the server.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getFILE_UPLOAD_ERROR()), 
					event.getFile().getFileName());
		}
	}
	
	/**
	 * Generates a new payment request.
	 */
	public void requestPaymentRequest() {
		try {
			logger.info("Generating a payment request...");
			Map<String, Object> params = new HashMap<>();
			List<BuyProof> buyProofsToUpdate = userProductHistorialService.obtainBuyProofsReadyToPay(user);
			this.updateUserBudget();
			long paymentRequestId = paymentRequest.getNewSequenceValue();
			double paymentRequestAmount = this.readyAmount;
			paymentRequest.setPaymentRequestId(paymentRequestId);
			paymentRequest.setAmount(paymentRequestAmount);
			//Populate parameters
			params.put(PAYMENT_REQUEST, paymentRequest);
			params.put(BUY_PROOFS_TO_UPDATE, buyProofsToUpdate);
			params.put(PAYMENT_REQUEST_ID, paymentRequestId);
			paymentRequestService.generatePaymentRequest(params);
			//Update payment request data table and user budget
			updatePaymentRequests();
			updateBuyProofsListHistory();
			updateUserBudget();
		} catch (BusinessServiceException e) {
			final String ERROR_MSG = "A service error has ocurred while generating a payment request.";
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		} catch (Exception e) {
			final String ERROR_MSG = "An error has ocurred while generating a payment request.";
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Updates the content of the buy proofs list.
	 */
	public void updateBuyProofsListHistory() {
		try {
			logger.info("Updating the buy proofs list...");
			buyProofsHistory = buyProofService.getBuyProofsByUser(params);
		} catch (Exception e) {
			logger.error("Error while updating the buy proofs list.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
	}
	
	/**
	 * Updates the list of fingerprints from the completed buys.
	 */
	public void updateUserProductHistorial() {
		try {
			logger.info("Updating the user product historial list...");
			userProductHistorials = userProductHistorialService
					.getCompletedHistorialByUser(params);
		} catch (Exception e) {
			logger.error("Error while updating the user product historial list.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
	}
	
	/**
	 * Updates the list of payment requests made by the user.
	 */
	public void updatePaymentRequests() {
		try {
			logger.info("Updating the user product historial list...");
			paymentRequestHistory = paymentRequestService.
					getPaymentRequestsByUser(user.getUserId());
		} catch (Exception e) {
			logger.error("Error while updating the user product historial list.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
	}
	
	/**
	 * Instantiates a new Buy Proof object.
	 */
	public void initializeBuyProof() {
		this.buyProof = new BuyProof();
		Status status = new Status();
		status.setStatusId(Statuses.VALIDATION_PENDING.getId());
		//Set object values
		this.buyProof.setUser(user);
		this.buyProof.setRegisteredOn(new Date());
		this.buyProof.setLastUpdate(new Date());
		this.buyProof.setPaybackApplied(false);
		this.buyProof.setStatus(status);
	}
	
	/**
	 * Instantiates a new Payment Request object.
	 */
	public void initializePaymentRequest() {
		this.paymentRequest = new PaymentRequest();
		Status status = new Status();
		status.setStatusId(Statuses.VALIDATION_PENDING.getId());
		this.paymentRequest.setUser(user);
		this.paymentRequest.setLastUpdate(new Date());
		this.paymentRequest.setRequestDate(new Date());
		this.paymentRequest.setStatus(status);
	}
	
	/**
	 * Downloads the buy proof files from the specified object.
	 * @param buyProof
	 * @return
	 */
	public StreamedContent downloadBuyProofObject(BuyProof buyProof) {
		logger.info("Downloading buy proof file...");
		StreamedContent streamedContent = null;
		try {
			InputStream inpStream = new ByteArrayInputStream(buyProof.getTicketImage());
			streamedContent = new DefaultStreamedContent(inpStream, buyProof.getContentType(), 
				buyProof.getFileName());
		} catch (Exception e) {
			logger.error("An error has ocurred while downloading the buy proof :" 
					+ buyProof.getBuyProofId(), e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
		return streamedContent;
	}
	
	/**
	 * Updates the amount of money in each section of the user's budget.
	 */
	public void updateUserBudget() {
		try {
			UserBudget userBudget = userProductHistorialService.obtainReadyOnWaitPaybackAmounts(user);
			this.readyAmount = userBudget.getAmountReadyToPay();
			this.onwaitAmount = userBudget.getAmountOnHold();
		} catch (BusinessServiceException e) {
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Validates if a payment request has a resolution date.
	 * @param paymentRequest
	 * @return
	 */
	public boolean pymntReqResolutionDateExist(PaymentRequest paymentRequest) {
		return paymentRequest != null && paymentRequest.getResolutionDate() != null; 
	}
	
	/**
	 * Validates if there are money available for a payment request.
	 * @return
	 */
	public boolean isBugetForDepositsAvailable() {
		return readyAmount > 0.0 && readyAmount >= MIN_PAYMENT_REQUEST;
	}

	public int getMAX_MEDIA_SIZE() {
		return MAX_MEDIA_SIZE;
	}

	public PaymentRequest getPaymentRequest() {
		return paymentRequest;
	}

	public void setPaymentRequest(PaymentRequest paymentRequest) {
		this.paymentRequest = paymentRequest;
	}

	public List<PaymentRequest> getPaymentRequestHistory() {
		return paymentRequestHistory;
	}

	public void setPaymentRequestHistory(List<PaymentRequest> paymentRequestHistory) {
		this.paymentRequestHistory = paymentRequestHistory;
	}

	public BuyProof getBuyProof() {
		return buyProof;
	}

	public void setBuyProof(BuyProof buyProof) {
		this.buyProof = buyProof;
	}
	
	public List<BuyProof> getBuyProofsHistory() {
		return buyProofsHistory;
	}

	public List<UserProductHistorial> getUserProductHistorials() {
		return userProductHistorials;
	}
	
	public List<UserProductHistorial> getUpdatedUserProductHistorials() {
		updateUserProductHistorial();
		return userProductHistorials;
	}
	
	public double getOnwaitAmount() {
		return onwaitAmount;
	}

	public double getReadyAmount() {
		return readyAmount;
	}
}
