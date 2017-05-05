package japhet.sales.controller.manager;

import static japhet.sales.catalogs.Statuses.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import japhet.sales.catalogs.Roles;
import japhet.sales.catalogs.Statuses;
import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.PaybackProtest;
import japhet.sales.model.impl.Status;
import japhet.sales.service.IBuyProofService;
import japhet.sales.service.IPaybackProtestService;

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
	
	//View attributes
	private List<BuyProof> buyProofs;
	private Map<Long, List<PaybackProtest>> paybackProtestsByBproof;
	
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
			
			Status paymentReqStatus = new Status();
			paymentReqStatus.setStatusId(Statuses.ON_PAYMENT_REQUEST.getId());
			
			statusList.add(pendingStatus);
			statusList.add(caseRaisedStatus);
			statusList.add(paymentReqStatus);
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
			paybackProtestService.acceptPaybackProtest(paybackProtest);
			updateBuyProofs();
			updatePaybackProtestsByBproof();
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
			paybackProtestService.rejectPaybackProtest(paybackProtest, bProofProtests);
			updateBuyProofs();
			updatePaybackProtestsByBproof();
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
}
