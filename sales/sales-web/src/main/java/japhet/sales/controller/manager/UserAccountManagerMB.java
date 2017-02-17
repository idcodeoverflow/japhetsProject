package japhet.sales.controller.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing user account manager...");
			//Initialize session properties
			params = new HashMap<>();
			paymentRequest = new PaymentRequest();
			user = getLoggedUser();
			params.put(USER_ID, user.getUserId());
			//Obtain user values
			paymentRequestHistory = paymentRequestService.
					getPaymentRequestsByUser(user.getUserId());
			initializeBuyProof();
			//Update elements
			updateBuyProofsListHistory();
			updateUserProductHistorial();
			updateReadyAmount();
			updateOnwaitAmount();
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
			updateReadyAmount();
			updateOnwaitAmount();
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
	 * Instantiates a new Buy Proof object.
	 */
	public void initializeBuyProof() {
		buyProof = new BuyProof();
		Status status = new Status();
		status.setStatusId(Statuses.VALIDATION_PENDING.getId());
		//Set object values
		buyProof.setUser(user);
		buyProof.setRegisteredOn(new Date());
		buyProof.setLastUpdate(new Date());
		buyProof.setPaybackApplied(false);
		buyProof.setStatus(status);
	}
	
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
	
	public List<String> getDeposits() {
		List<String> deposits = new ArrayList<>();
		deposits.add("182646");
		deposits.add("182878");
		deposits.add("182656");
		deposits.add("182686");
		return deposits;
	}
	
	public void updateReadyAmount() {
		try {
			this.readyAmount = userProductHistorialService.obtainReadyPaybackAmount(user);
		} catch (BusinessServiceException e) {
			showGeneralExceptionMessage();
		}
	}
	
	public void updateOnwaitAmount() {
		
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
