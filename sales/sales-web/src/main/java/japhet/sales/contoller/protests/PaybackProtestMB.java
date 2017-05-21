package japhet.sales.contoller.protests;

import static japhet.sales.mailing.MailingParameters.BUYPROOF_ID;
import static japhet.sales.mailing.MailingParameters.NAME;
import static japhet.sales.mailing.MailingParameters.PBACK_PROTES_ID;
import static japhet.sales.mailing.MailingTemplates.PBACK_PROTEST_RAISED;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import japhet.sales.catalogs.Statuses;
import japhet.sales.controller.GenericMB;
import japhet.sales.mailing.ContentTypes;
import japhet.sales.mailing.service.IMailingService;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.PaybackProtest;
import japhet.sales.model.impl.Status;
import japhet.sales.model.impl.User;
import japhet.sales.service.IPaybackProtestService;
import japhet.sales.service.IUtilService;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@ManagedBean
@ViewScoped
public class PaybackProtestMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -8231225380689786773L;

	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IPaybackProtestService paybackProtestService;
	
	@EJB
	private IUtilService utilService;
	
	@EJB
	private IMailingService mailingService;
	
	//Input properties
	private byte []fileBytes;
	private String description;
	
	//Logic properties
	private User user;
	private PaybackProtest paybackProtest;
	private BuyProof buyProof;
	
	private String fileName;
	private String contentType;
	
	/**
	 * Initializes the data and services required for this MB.
	 */
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing PaybackProtestMB...");
			this.user = getLoggedUser();
		} catch (Exception e) {
			logger.error("An error has ocurred while initializing PaybackProtestMB.", e);
			showStartupMbExceptionMessage();
		}
	}
	
	/**
	 * Persists a new PaybackProtes into the DB.
	 * @param buyProof
	 */
	public void generatePaybackProtest() {
		try {
			logger.info("Generating a new PaybackProtest...");
			populatePaybackProtest();
			paybackProtestService.insertPaybackProtest(paybackProtest);
			//Send email notifications
			sendPaybackProtestEmailToCompany(paybackProtest);
			sendPaybackProtestEmail(paybackProtest);
			//Clear UI fields
			this.description = "";
			this.fileBytes = null;
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSUCCESFUL_PAYBACK_PROTEST()), "");
		} catch (Exception e) {
			logger.error("An error has occured while trying to generate a new PaybackProtest.", e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Populates a new PaybackProtest
	 */
	private void populatePaybackProtest() {
		paybackProtest = new PaybackProtest();
		Company company = new Company();
		company.setCompanyId(this.buyProof.getUserProductHistorial()
				.getProduct().getCompany().getCompanyId());
		Status status = new Status();
		status.setStatusId(Statuses.VALIDATION_PENDING.getId());
		paybackProtest.setCompany(company);
		paybackProtest.setBuyProof(this.buyProof);
		paybackProtest.setDescription(this.description);
		paybackProtest.setCreatedOn(new Date());
		paybackProtest.setFileContent(this.fileBytes);
		paybackProtest.setLastUpdate(new Date());
		paybackProtest.setStatus(status);
		paybackProtest.setContentType(this.contentType);
		paybackProtest.setFileName(this.fileName);
	}
	
	/**
	 * Handles the stream upload of a file with 
	 * more information about the PaybackProtest.
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
        try {
			this.fileBytes = utilService.getBiteArrayFromStream(
					event.getFile().getInputstream());
			this.contentType = event.getFile().getContentType();
			this.fileName = event.getFile().getFileName();
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getIMAGE_READY()), "");
		} catch (Exception e) {
			logger.error("There was an error uploading the file to the server.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getIMAGE_UPLOAD_ERROR()), 
					event.getFile().getFileName());
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
		mailingService.sendMessage(PBACK_PROTEST_RAISED.getSubject(), 
				buyProof.getUser().getEmail(), 
				PBACK_PROTEST_RAISED, 
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
		//Set mailing parameters
		params.put(NAME, this.user.getName());
		params.put(BUYPROOF_ID, buyProof.getBuyProofId());
		params.put(PBACK_PROTES_ID, paybackProtest.getPaybackProtestId());
		mailingService.sendMessage(PBACK_PROTEST_RAISED.getSubject(), 
				this.user.getEmail(), 
				PBACK_PROTEST_RAISED, 
				ContentTypes.TEXT_HTML, 
				params);
	}
	
	/**
	 * @return the fileBytes
	 */
	public byte[] getFileBytes() {
		return fileBytes;
	}

	/**
	 * @param fileBytes the fileBytes to set
	 */
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public BuyProof getBuyProof() {
		return buyProof;
	}

	public void setBuyProof(BuyProof buyProof) {
		this.buyProof = buyProof;
	}
}
