package japhet.sales.controller.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

import japhet.sales.catalogs.Statuses;
import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.PaybackProtest;
import japhet.sales.model.impl.Product;
import japhet.sales.model.impl.Status;
import japhet.sales.model.impl.User;
import japhet.sales.service.IBuyProofService;
import japhet.sales.service.ICompanyService;
import japhet.sales.service.IPaybackProtestService;
import japhet.sales.service.IProductService;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@ManagedBean
@ViewScoped
public class CompanyAccountManagerMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -2897730684197796902L;
	
	//Table projects
	private List<Product> companyProducts;
	private List<BuyProof> buyProofsOnPaymentRequests;
	
	//Tables summary
	private double buysTotalSum;
	private double buysAforeSum;
	private int totalBuysCount;
	private double totalOrdersSum;
	private double totalAcceptedOrdersSum;
	private int totalOrdersCount;
	
	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IProductService productService;
	
	@EJB
	private IBuyProofService buyProofService;
	
	@EJB
	private ICompanyService companyService;
	
	@EJB
	private IPaybackProtestService paybackProtestService;
		
	//Logic attributes
	private User user;
	private Company company;
	private Map<Long, List<PaybackProtest>> paybackProtestsPerBProof;
	
	/**
	 * Initializes the content of this MB.
	 */
	@PostConstruct
	public void init() {
		try {
			logger.info("Initializing the CompanyAccountManagerMB...");
			//If the current user is not a company exit
			if(getLoggedCompany() == null) {
				return;
			}
			//Update logic attributes
			this.user = getLoggedUser();
			this.company = obtainCompanyByUser(user);
			this.paybackProtestsPerBProof = new HashMap<>();
			//Initialize data tables
			updateCompanyProducts();
			updateBuyProofsOnPaymentRequests();
			updateProtestsByBuyProof();
		} catch (Exception e) {
			logger.error("An error has ocurred while initializing the CompanyAccountManagerMB.", e);
			showStartupMbExceptionMessage();
		}
	}
	
	/**
	 * Updates the content of the list that 
	 * feeds the company products data table.
	 */
	private void updateCompanyProducts() throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		final Long COMP_ID = ((this.company != null 
				&& this.company.getCompanyId() != null) ? this.company.getCompanyId() : -1L);
		final String INFO_MSG = String.format("Updating Company Products for the Company %d...", COMP_ID);
		try {
			logger.info(INFO_MSG);
			parameters.put(COMPANY_ID, this.company.getCompanyId());
			this.companyProducts = productService.getAvailableProductsFromCompany(parameters);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while updating the company %d products.", COMP_ID);
			logger.error(ERROR_MSG, e);
			throw new Exception(ERROR_MSG, e);
		}
	}
	
	/**
	 * Updates the content of the list that
	 * feeds the company payment requests data table.
	 */
	private void updateBuyProofsOnPaymentRequests() throws Exception {
		Map<String, Object> params = new HashMap<>();
		final Long COMP_ID = ((this.company != null 
				&& this.company.getCompanyId() != null) ? this.company.getCompanyId() : -1L);
		final Short VALIDATION_PENDING_STATUS_ID = Statuses.VALIDATION_PENDING.getId();
		final Short CASE_RAISED_STATUS_ID = Statuses.CASE_RAISED.getId();
		final String INFO_MSG = String.format("Updating Payment Requests for the Company: %d and Status: %d, %d...", 
				COMP_ID, VALIDATION_PENDING_STATUS_ID, CASE_RAISED_STATUS_ID);
		try {
			logger.info(INFO_MSG);
			params.put(COMPANY_ID, this.company.getCompanyId());
			List<Status> statuses = new ArrayList<>();
			Status onRequest = new Status();
			onRequest.setStatusId(VALIDATION_PENDING_STATUS_ID);
			Status caseRaised = new Status();
			caseRaised.setStatusId(CASE_RAISED_STATUS_ID);
			statuses.add(onRequest);
			statuses.add(caseRaised);
			params.put(STATUS_ID, statuses);

			//Remove from the list elements that doesn't match the required dates
			List<BuyProof> unfilteredBProofs = buyProofService.getBuyProofsByCompanyAndStatus(params);
			this.buyProofsOnPaymentRequests = new ArrayList<>();
			if(unfilteredBProofs != null) {
				final Date TODAY = new Date();
				for(BuyProof buyProof : unfilteredBProofs) {
					final Date UPLOADED_DATE = buyProof.getRegisteredOn();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(UPLOADED_DATE);
					final Company company = buyProof.getUserProductHistorial().getProduct().getCompany();
					//Obtain the 50% of the days rounded up
					final short DAYS_TO_ADD = (short)Math.ceil((double)company.getDaysNumberToApprove() / 2.0);
					calendar.add(Calendar.DATE, DAYS_TO_ADD);
					final Date REQUIRED_DATE = calendar.getTime();
					if(TODAY.before(REQUIRED_DATE)) {
						this.buyProofsOnPaymentRequests.add(buyProof);
					}
				}
			}
			//Set list to null and hopefully help to free space from the heap faster
			unfilteredBProofs = null;
			
			//Update the buys statistics
			this.updateBuysTotalSum(buyProofsOnPaymentRequests);
			this.updateBuysAforeSum(buyProofsOnPaymentRequests);
			this.updateBuysCount(buyProofsOnPaymentRequests);
		} catch (Exception e) {
			final String ERROR_MSG = String.
					format("An error has ocurred while updating the Company: %d Payment Requests and Status: %d.", COMP_ID, VALIDATION_PENDING_STATUS_ID);
			logger.error(ERROR_MSG, e);
			throw new Exception(ERROR_MSG, e);
		}
	}
	
	/**
	 * This method updates the PaybackProtests by BuyProof and Company.
	 * @throws Exception
	 */
	public void updateProtestsByBuyProof() throws Exception {
		final Long COMP_ID = ((this.company != null 
				&& this.company.getCompanyId() != null) ? this.company.getCompanyId() : -1L);
		final String INFO_MSG = String.format("Updating the PaybackProtests by BuyProof Company: %d...", COMP_ID);
		try {
			logger.info(INFO_MSG);
			this.paybackProtestsPerBProof.clear();
			Map<String, Object> params = new HashMap<>();
			params.put(COMPANY_ID, this.company.getCompanyId());
			List<PaybackProtest> paybackProtests = paybackProtestService.getPaybackProtestsByCompany(params);
			if(paybackProtests == null) {
				return;
			}
			for(PaybackProtest paybackProtest : paybackProtests) {
				long buyProofId = paybackProtest.getBuyProof().getBuyProofId();
				if(paybackProtestsPerBProof.containsKey(buyProofId)) {
					List<PaybackProtest> protestsByBProofId = paybackProtestsPerBProof.get(buyProofId);
					protestsByBProofId.add(paybackProtest);
					paybackProtestsPerBProof.put(paybackProtest.getBuyProof().getBuyProofId(), protestsByBProofId);
				} else {
					List<PaybackProtest> protestsByBProofId = new ArrayList<>();
					protestsByBProofId.add(paybackProtest);
					paybackProtestsPerBProof.put(paybackProtest.getBuyProof().getBuyProofId(), protestsByBProofId);
				}
			}
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while updating the PaybackProtests by BuyProof Company: %d.", COMP_ID);
			logger.error(ERROR_MSG, e);
			throw new Exception(ERROR_MSG, e);
		}
	}
	
	/**
	 * Updates the amount that represents the total sum 
	 * of the buy proofs that are in payment request
	 * process.
	 * @param buyProofs buy proofs list to from the 
	 * one the info is going to be obtained.
	 */
	private void updateBuysTotalSum(List<BuyProof> buyProofs) throws Exception {
		this.buysTotalSum = 0.0;
		if(buyProofs != null) {
			for(BuyProof buyProof : buyProofs) {
				this.buysTotalSum += buyProof.getTotal();
			}
		}
	}
	
	/**
	 * Updates the amount that represents the total sum for the Afore
	 * of the buy proofs that are in payment request
	 * process.
	 * @param buyProofs buy proofs list to from the 
	 * one the info is going to be obtained.
	 */
	private void updateBuysAforeSum(List<BuyProof> buyProofs) throws Exception {
		this.buysAforeSum = 0.0;
		if(buyProofs != null) {
			for(BuyProof buyProof : buyProofs) {
				this.buysAforeSum += buyProof.getUserProductHistorial().getPaybackAmount();
			}
		}
	}
	
	/**
	 * Updates the amount that represents the total count 
	 * of the buy proofs that are in payment request
	 * process.
	 * @param buyProofs buy proofs list to from the 
	 * one the info is going to be obtained.
	 */
	private void updateBuysCount(List<BuyProof> buyProofs) throws Exception {
		this.totalBuysCount = 0;
		if(buyProofs != null) {
			totalBuysCount = buyProofs.size();
		}
	}
	
	/**
	 * Ends the product validity from the specified product.
	 * @param product Product to end validity.
	 */
	public void endProductValidity(Product product) {
		final long PRODUCT_ID = ((product != null) ? product.getProductId() : -1L);
		final String INFO_MSG = String.format("Ending product validity: %d...", PRODUCT_ID);
		try {
			logger.info(INFO_MSG);
			//Update end date
			product.setEndDate(new Date());
			productService.updateProductAndFlush(product);
			updateCompanyProducts();
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while ending product validity for id: %d", PRODUCT_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
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
	 * Downloads the PaybackProtest files from the specified object.
	 * @param paybackProtest
	 * @return
	 */
	public StreamedContent downloadPaybackProtestObject(PaybackProtest paybackProtest) {
		logger.info("Downloading PaybackProtest file...");
		StreamedContent streamedContent = null;
		try {
			InputStream inpStream = new ByteArrayInputStream(paybackProtest.getFileContent());
			streamedContent = new DefaultStreamedContent(inpStream, paybackProtest.getContentType(), 
					paybackProtest.getFileName());
		} catch (Exception e) {
			logger.error("An error has ocurred while downloading the PaybackProtest:" 
					+ paybackProtest.getPaybackProtestId(), e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
		return streamedContent;
	}
	
	/**
	 * Accepts the order specified.
	 * @param order Order to accept
	 */
	public void acceptOrder(Object order) {
		final long ORDER_ID = 0L;
		final String INFO_MSG = String.format("Accepting product order with id: %d...", ORDER_ID);
		try {
			logger.info(INFO_MSG);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while accepting the order id: %", ORDER_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Declines the order specified.
	 * @param order Order to decline
	 */
	public void declineOrder(Object order) {
		final long ORDER_ID = 0L;
		final String INFO_MSG = String.format("Declining product order with id: %d...", 0);
		try {
			logger.info(INFO_MSG);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while declining the order id: %", ORDER_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Completes the order specified.
	 * @param order Order to complete
	 */
	public void completeOrder(Object order) {
		final long ORDER_ID = 0L;
		final String INFO_MSG = String.format("Completing product order with id: %d...", 0);
		try {
			logger.info(INFO_MSG);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while completing the order id: %", ORDER_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Obtain the Company object that is linked to the User.
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private Company obtainCompanyByUser(User user) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(USER_ID, user.getUserId());
		Company company = companyService.getCompanyByUserId(parameters);
		return company;
	}
	
	public List<PaybackProtest> getPaybackProtestsByBProof(long buyProofId) {
		if(paybackProtestsPerBProof == null) {
			return new ArrayList<PaybackProtest>();
		}
		return paybackProtestsPerBProof.get(buyProofId);
	}
	
	public List<Product> getCompanyProducts() {
		return companyProducts;
	}
	
	public void setCompanyProducts(List<Product> companyProducts) {
		this.companyProducts = companyProducts;
	}
	
	public List<BuyProof> getBuyProofsOnPaymentRequests() {
		return buyProofsOnPaymentRequests;
	}
	
	public void setBuyProofsOnPaymentRequests(List<BuyProof> buyProofsOnPaymentRequests) {
		this.buyProofsOnPaymentRequests = buyProofsOnPaymentRequests;
	}

	public double getBuysTotalSum() {
		return buysTotalSum;
	}

	public void setBuysTotalSum(double buysTotalSum) {
		this.buysTotalSum = buysTotalSum;
	}

	public double getBuysAforeSum() {
		return buysAforeSum;
	}

	public void setBuysAforeSum(double buysAforeSum) {
		this.buysAforeSum = buysAforeSum;
	}

	public int getTotalBuysCount() {
		return totalBuysCount;
	}

	public void setTotalBuysCount(int totalBuysCount) {
		this.totalBuysCount = totalBuysCount;
	}

	public double getTotalOrdersSum() {
		return totalOrdersSum;
	}

	public void setTotalOrdersSum(double totalOrdersSum) {
		this.totalOrdersSum = totalOrdersSum;
	}

	public double getTotalAcceptedOrdersSum() {
		return totalAcceptedOrdersSum;
	}

	public void setTotalAcceptedOrdersSum(double totalAcceptedOrdersSum) {
		this.totalAcceptedOrdersSum = totalAcceptedOrdersSum;
	}

	public int getTotalOrdersCount() {
		return totalOrdersCount;
	}

	public void setTotalOrdersCount(int totalOrdersCount) {
		this.totalOrdersCount = totalOrdersCount;
	}
}
