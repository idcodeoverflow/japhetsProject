package japhet.sales.controller.manager;

import static japhet.sales.catalogs.Statuses.*;
import static japhet.sales.mailing.MailingParameters.*;
import static japhet.sales.mailing.MailingTemplates.*;

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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import japhet.sales.catalogs.Roles;
import japhet.sales.catalogs.Statuses;
import japhet.sales.controller.GenericMB;
import japhet.sales.mailing.ContentTypes;
import japhet.sales.mailing.MailingParameters;
import japhet.sales.mailing.service.IMailingService;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.PaybackProtest;
import japhet.sales.model.impl.PaymentRequest;
import japhet.sales.model.impl.Status;
import japhet.sales.service.IBuyProofService;
import japhet.sales.service.IPaybackProtestService;
import japhet.sales.service.IPaymentRequestService;

@ManagedBean
@ViewScoped
public class AdministratorAccountManagerMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1328228536288953258L;

	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IBuyProofService buyProofService;
	
	@EJB
	private IPaybackProtestService paybackProtestService;
	
	@EJB
	private IPaymentRequestService paymentRequestService;
	
	@EJB
	private IMailingService mailingService;
	
	//View attributes
	private List<BuyProof> buyProofs;
	private Map<Long, List<PaybackProtest>> paybackProtestsByBproof;
	private List<PaymentRequest> paymentRequests;
	
	@PostConstruct
	private void init() {
		final String INFO_MSG = "Initializing AdministratorAccountManagerMB...";
		try {
			logger.info(INFO_MSG);
			//If the current user is not an administrator exit of the process
			if(getLoggedUser().getRole().getRoleId() != Roles.ADMINISTRATOR.getId()) {
				return;
			}
			//Update view elements
			updateBuyProofs();
			updatePaybackProtestsByBproof();
			updatePaymentRequests();
		} catch(Exception e) {
			final String ERROR_MSG = "An error has occurred while initializing the AdministratorAccountManagerMB.";
			logger.error(ERROR_MSG, e);
			showStartupMbExceptionMessage();
		}
	}
	
	private void updateBuyProofs() throws Exception {
		final String INFO_MSG = "Updating the buyProof list...";
		try {
			logger.info(INFO_MSG);
			//Query params
			Map<String, Object> params = new HashMap<>();
			List<Status> statusList = new ArrayList<>();
			//Buy proof statuses
			Status pendingStatus = new Status();
			pendingStatus.setStatusId(Statuses.VALIDATION_PENDING.getId());
			
			Status caseRaisedStatus = new Status();
			caseRaisedStatus.setStatusId(Statuses.CASE_RAISED.getId());
			
			statusList.add(pendingStatus);
			statusList.add(caseRaisedStatus);
			params.put(STATUS_ID, statusList);
			this.buyProofs = buyProofService.getBuyProofsByStatus(params);
		} catch(Exception e) {
			final String ERROR_MSG = "An error has occurred the buyProof list.";
			logger.error(ERROR_MSG, e);
			throw new Exception(ERROR_MSG, e);
		}
	}
	
	private void updatePaybackProtestsByBproof() throws Exception {
		final String INFO_MSG = "Updating the PaybackProtests by BuyProof...";
		try {
			logger.info(INFO_MSG);
			this.paybackProtestsByBproof = new HashMap<>();
			List<PaybackProtest> paybackProtests = this.paybackProtestService.getAllPaybackProtests();
			if(paybackProtests == null) {
				return;
			}
			for(PaybackProtest paybackProtest : paybackProtests) {
				long buyProofId = paybackProtest.getBuyProof().getBuyProofId();
				if(this.paybackProtestsByBproof.containsKey(buyProofId)) {
					List<PaybackProtest> protestsByBProofId = this.paybackProtestsByBproof.get(buyProofId);
					protestsByBProofId.add(paybackProtest);
					this.paybackProtestsByBproof.put(paybackProtest.getBuyProof().getBuyProofId(), protestsByBProofId);
				} else {
					List<PaybackProtest> protestsByBProofId = new ArrayList<>();
					protestsByBProofId.add(paybackProtest);
					this.paybackProtestsByBproof.put(paybackProtest.getBuyProof().getBuyProofId(), protestsByBProofId);
				}
			}
		} catch(Exception e) {
			final String ERROR_MSG = "An error has occurred while updating the Protests by BuyProof.";
			logger.error(ERROR_MSG, e);
			throw new Exception(ERROR_MSG, e);
		}
	}
	
	private void updatePaymentRequests() throws Exception {
		final String INFO_MSG = "Updating the PaymentRequest list...";
		try {
			logger.info(INFO_MSG);
			final short PENDING_STATUS = Statuses.VALIDATION_PENDING.getId();
			this.paymentRequests = paymentRequestService.getPaymentRequestsByStatus(PENDING_STATUS);
		} catch(Exception e) {
			final String ERROR_MSG = "An error has occurred the PaymentRequest list.";
			logger.error(ERROR_MSG, e);
			throw new Exception(ERROR_MSG, e);
		}
	}

	/**
	 * Downloads the BuyProof file from the specified object.
	 * @param paybackProtest
	 * @return
	 */
	public StreamedContent downloadBuyProofObject(BuyProof buyProof) {
		final String INFO_MSG = "Downloading BuyProof file...";
		StreamedContent streamedContent = null;
		try {
			logger.info(INFO_MSG);
			InputStream inpStream = new ByteArrayInputStream(buyProof.getTicketImage());
			streamedContent = new DefaultStreamedContent(inpStream, buyProof.getContentType(), 
					buyProof.getFileName());
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has ocurred while downloading the BuyProof: %d.", buyProof.getBuyProofId());
			logger.error(ERROR_MSG, e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
		return streamedContent;
	}
	
	/**
	 * Change the status of BuyProof to validated.
	 * @param buyProof
	 */
	public void validateBuyProof(BuyProof buyProof) {
		final long BPROOF_ID = ((buyProof != null 
				&& buyProof.getBuyProofId() != null) ? buyProof.getBuyProofId() : -1L);
		try {
			final String INFO_MSG = String.format("Validating BuyProof: %d...", BPROOF_ID);
			if(buyProof == null) {
				return;
			}
			logger.info(INFO_MSG);
			Status status = new Status();
			status.setStatusId(Statuses.VALIDATED.getId());
			buyProof.setStatus(status);
			buyProofService.updateBuyProof(buyProof);
			updateBuyProofs();
			sendBProofEmail(buyProof);
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getBUY_PROOF_VALIDATED()), "");
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while validating BuyProof: %d.", BPROOF_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Changes the status of a BuyProof to rejected.
	 * @param buyProof
	 */
	public void rejectBuyProof(BuyProof buyProof) {
		final long BPROOF_ID = ((buyProof != null 
				&& buyProof.getBuyProofId() != null) ? buyProof.getBuyProofId() : -1L);
		try {
			final String INFO_MSG = String.format("Rejecting BuyProof: %d...", BPROOF_ID);
			if(buyProof == null) {
				return;
			}
			logger.info(INFO_MSG);
			Status status = new Status();
			status.setStatusId(Statuses.DENIED.getId());
			buyProof.setStatus(status);
			buyProofService.updateBuyProof(buyProof);
			updateBuyProofs();
			sendBProofEmail(buyProof);
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getBUY_PROOF_REJECTED()), "");
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while rejecting BuyProof: %d.", BPROOF_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * This method sends a notification email to the 
	 * buy proof owner to notify about the status update.
	 * @param buyProof
	 * @throws Exception
	 */
	private void sendBProofEmail(BuyProof buyProof) throws Exception {
		Map<String, Object> params = new HashMap<>();
		//Set mailing parameters
		params.put(NAME, buyProof.getUser().getName());
		params.put(BUYPROOF_ID, buyProof.getBuyProofId());
		mailingService.sendMessage(BUYPROOF_UPDATED.getSubject(), 
				buyProof.getUser().getEmail(), 
				BUYPROOF_UPDATED, 
				ContentTypes.TEXT_HTML, 
				params);
	}
	
	/**
	 * Downloads the PaybackProtest files from the specified object.
	 * @param paybackProtest
	 * @return
	 */
	public StreamedContent downloadPaybackProtestObject(PaybackProtest paybackProtest) {
		final String INFO_MSG = "Downloading PaybackProtest file...";
		StreamedContent streamedContent = null;
		try {
			logger.info(INFO_MSG);
			InputStream inpStream = new ByteArrayInputStream(paybackProtest.getFileContent());
			streamedContent = new DefaultStreamedContent(inpStream, paybackProtest.getContentType(), 
					paybackProtest.getFileName());
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has ocurred while downloading the PaybackProtest: %d", paybackProtest.getPaybackProtestId());
			logger.error(ERROR_MSG, e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
		return streamedContent;
	}
	
	/**
	 * Changes the status to Accepted of a PaybackProtest.
	 * @param paybackProtest
	 */
	public void acceptProtest(PaybackProtest paybackProtest) {
		final long PROTEST_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		try {
			final String INFO_MSG = String.format("Accepting PaybackProtest: %d...", PROTEST_ID);
			if(paybackProtest == null) {
				return;
			}
			logger.info(INFO_MSG);
			paybackProtest.setResolutionDate(new Date());
			paybackProtestService.acceptPaybackProtest(paybackProtest);
			updateBuyProofs();
			updatePaybackProtestsByBproof();
			sendPaybackProtestEmailToCompany(paybackProtest);
			sendPaybackProtestEmail(paybackProtest);
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getPAYBACK_PROTEST_VALIDATED()), "");
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while accepting the PaybackProtest: %d.", PROTEST_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Changes the status to Rejected of a PaybackProtest.
	 * @param paybackProtest
	 */
	public void rejectProtest(PaybackProtest paybackProtest) {
		final long PROTEST_ID = ((paybackProtest != null 
				&& paybackProtest.getPaybackProtestId() != null) ? paybackProtest.getPaybackProtestId() : -1L);
		try {
			final String INFO_MSG = String.format("Rejecting PaybackProtest: %d...", PROTEST_ID);
			if(paybackProtest == null) {
				return;
			}
			logger.info(INFO_MSG);
			final long bProofId = paybackProtest.getBuyProof().getBuyProofId();
			List<PaybackProtest> bProofProtests = getPaybackProtestsByBProof(bProofId);
			paybackProtest.setResolutionDate(new Date());
			paybackProtestService.rejectPaybackProtest(paybackProtest, bProofProtests);
			updateBuyProofs();
			updatePaybackProtestsByBproof();
			sendPaybackProtestEmailToCompany(paybackProtest);
			sendPaybackProtestEmail(paybackProtest);
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getPAYBACK_PROTEST_REJECTED()), "");
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while rejecting the PaybackProtest: %d.", PROTEST_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * This method sends a notification email to the 
	 * buy proof owner to notify about the payback protest 
	 * status update.
	 * @throws Exception
	 */
	private void sendPaybackProtestEmail(PaybackProtest paybackProtest) throws Exception {
		Map<String, Object> params = new HashMap<>();
		BuyProof buyProof = paybackProtest.getBuyProof();
		//Set mailing parameters
		params.put(NAME, buyProof.getUser().getName());
		params.put(BUYPROOF_ID, buyProof.getBuyProofId());
		params.put(PBACK_PROTES_ID, paybackProtest.getPaybackProtestId());
		mailingService.sendMessage(PBACK_PROTEST_SOLVED.getSubject(), 
				buyProof.getUser().getEmail(), 
				PBACK_PROTEST_SOLVED, 
				ContentTypes.TEXT_HTML, 
				params);
	}
	
	/**
	 * This method sends a notification email to the 
	 * buy proof company to notify about the payback protest 
	 * status update.
	 * @throws Exception
	 */
	private void sendPaybackProtestEmailToCompany(PaybackProtest paybackProtest) throws Exception {
		Map<String, Object> params = new HashMap<>();
		BuyProof buyProof = paybackProtest.getBuyProof();
		Company company = paybackProtest.getCompany();
		//Set mailing parameters
		params.put(NAME, company.getUser().getName());
		params.put(BUYPROOF_ID, buyProof.getBuyProofId());
		params.put(PBACK_PROTES_ID, paybackProtest.getPaybackProtestId());
		mailingService.sendMessage(PBACK_PROTEST_SOLVED.getSubject(), 
				company.getUser().getEmail(), 
				PBACK_PROTEST_SOLVED, 
				ContentTypes.TEXT_HTML, 
				params);
	}
	
	/**
	 * This method sends the request to mark the specified 
	 * PaymentRequest to payed.
	 * @param paymentRequest
	 */
	public void confirmPaymentRequest(PaymentRequest paymentRequest) {
		final long PMNT_REQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("Trying to confirm the PaymentRequest: %d...", PMNT_REQ_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequestService.confirmPaymentRequest(paymentRequest);
			updatePaymentRequests();
			sendPaymentRequestEmail(paymentRequest);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while trying to confirm the PaymentRequest: %d.", PMNT_REQ_ID);
			logger.error(ERROR_MSG, e);
		}
	}
	
	/**
	 * This method sends the request to mark the specified 
	 * PaymentRequest to rejected.
	 * @param paymentRequest
	 */
	public void rejectPaymentRequest(PaymentRequest paymentRequest) {
		final long PMNT_REQ_ID = ((paymentRequest != null 
				&& paymentRequest.getPaymentRequestId() != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("Trying to reject the PaymentRequest: %d...", PMNT_REQ_ID);
		try {
			logger.info(INFO_MSG);
			paymentRequestService.rejectPaymentRequest(paymentRequest);
			updatePaymentRequests();
			sendPaymentRequestEmail(paymentRequest);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while trying to reject the PaymentRequest: %d.", PMNT_REQ_ID);
			logger.error(ERROR_MSG, e);
		}
	}
	
	/**
	 * This method sends a notification email to the 
	 * buy payment request owner about the status update. 
	 * status update.
	 * @throws Exception
	 */
	private void sendPaymentRequestEmail(PaymentRequest paymentRequest) throws Exception {
		Map<String, Object> params = new HashMap<>();
		//Set mailing parameters
		params.put(NAME, paymentRequest.getUser().getName());
		params.put(CURP, paymentRequest.getUser().getCurp());
		params.put(MailingParameters.PAYMENT_REQUEST_ID, paymentRequest.getPaymentRequestId());
		mailingService.sendMessage(PAYMENT_REQUEST_UPDATED.getSubject(), 
				paymentRequest.getUser().getEmail(), 
				PAYMENT_REQUEST_UPDATED, 
				ContentTypes.TEXT_HTML, 
				params);
	}
	
	/**
	 * Determines if the validate button is going to be enabled.
	 * @param buyProof
	 * @return
	 */
	public boolean enableValidateBProof(BuyProof buyProof) {
		return buyProof != null 
				&& buyProof.getStatus() != null
				&& buyProof.getStatus().getStatusId() != null
				&& buyProof.getStatus().getStatusId() == ON_PAYMENT_REQUEST.getId();
	}
	
	/**
	 * Determines if the reject button is going to be enabled.
	 * @param buyProof
	 * @return
	 */
	public boolean enableRejectBProof(BuyProof buyProof) {
		return buyProof != null 
				&& buyProof.getStatus() != null
				&& buyProof.getStatus().getStatusId() != null
				&& buyProof.getStatus().getStatusId() == ON_PAYMENT_REQUEST.getId();
	}
	
	/**
	 * Determines if the accept payback protest button is going to be enabled.
	 * @param paybackProtest
	 * @return
	 */
	public boolean enableAcceptPaybackProtest(PaybackProtest paybackProtest) {
		return paybackProtest != null
				&& paybackProtest.getStatus() != null
				&& paybackProtest.getStatus().getStatusId() != null
				&& paybackProtest.getStatus().getStatusId() == VALIDATION_PENDING.getId();
	}
	
	/**
	 * Determines if the reject payback protest button is going to be enabled.
	 * @param paybackProtest
	 * @return
	 */
	public boolean enableRejectPaybackProtest(PaybackProtest paybackProtest) {
		return paybackProtest != null
				&& paybackProtest.getStatus() != null
				&& paybackProtest.getStatus().getStatusId() != null
				&& paybackProtest.getStatus().getStatusId() == VALIDATION_PENDING.getId();
	}
	
	/**
	 * Returns the List<PaybackProtest> from the buyProofId specified.
	 * @param buyProofId
	 * @return
	 */
	public List<PaybackProtest> getPaybackProtestsByBProof(long buyProofId) {
		if(this.paybackProtestsByBproof == null) {
			return new ArrayList<PaybackProtest>();
		}
		return this.paybackProtestsByBproof.get(buyProofId);
	}

	/**
	 * @return the buyProofs
	 */
	public List<BuyProof> getBuyProofs() {
		return buyProofs;
	}

	/**
	 * @param buyProofs the buyProofs to set
	 */
	public void setBuyProofs(List<BuyProof> buyProofs) {
		this.buyProofs = buyProofs;
	}

	/**
	 * @return the paymentRequests
	 */
	public List<PaymentRequest> getPaymentRequests() {
		return paymentRequests;
	}

	/**
	 * @param paymentRequests the paymentRequests to set
	 */
	public void setPaymentRequests(List<PaymentRequest> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}
}
