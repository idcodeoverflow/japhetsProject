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
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.PaymentRequest;
import japhet.sales.model.impl.Status;
import japhet.sales.model.impl.User;
import japhet.sales.service.IBuyProofService;
import japhet.sales.service.IPaymentRequestService;
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
	private IUtilService utilService;
	
	//Validation properties
	private final int MAX_MEDIA_SIZE = 1000000;
	
	//View properties
	private byte[] buyProofBytes;
	private PaymentRequest paymentRequest;
	private BuyProof buyProof;
	private List<PaymentRequest> paymentRequestHistory;
	private List<BuyProof> buyProofsHistory;
	
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
			initializeBuyProof();
			//Obtain user values
			paymentRequestHistory = paymentRequestService.
					getPaymentRequestsByUser(user.getUserId());
			updateBuyProofsListHistory();
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
			initializeBuyProof();
			//Persist into the DB
			buyProof.setFileName(event.getFile().getFileName());
			buyProof.setContentType(event.getFile().getContentType());
			buyProofService.insertBuyProof(buyProof);
			updateBuyProofsListHistory();
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
	 * Instantiates a new Buy Proof object.
	 */
	public void initializeBuyProof() {
		buyProof = new BuyProof();
		Status status = new Status();
		status.setStatusId(Statuses.VALIDATION_PENDING.getId());
		//Set object values
		buyProof.setUser(user);
		buyProof.setRegisteredOn(new Date());
		buyProof.setTicketImage(buyProofBytes);
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

	public void setBuyProofsHistory(List<BuyProof> buyProofsHistory) {
		this.buyProofsHistory = buyProofsHistory;
	}
}
