package japhet.sales.controller.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.PaymentRequest;
import japhet.sales.model.impl.Product;
import japhet.sales.model.impl.User;
import japhet.sales.service.IPaymentRequestService;
import japhet.sales.service.IProductService;

@ManagedBean
@ViewScoped
public class CompanyAccountManagerMB extends GenericMB {

	//Table projects
	private List<Product> companyProducts;
	private List<PaymentRequest> paymentRequests;
	
	//Tables summary
	private double buysTotalSum;
	private double buysAforeSum;
	private int totalBuysCount;
	private double totalOrdersSum;
	private double totalAcceptedOrdersSum;
	private int totalOrdersCount;
	
	private Logger logger;
	
	//EJB
	@EJB
	private IProductService productService;
	
	@EJB
	private IPaymentRequestService paymentRequestService;
	
	//Logic attributes
	private User user;
	
	/**
	 * Initializes the content of this MB.
	 */
	@PostConstruct
	public void init() {
		try {
			logger.info("Initializing the CompanyAccountManagerMB...");
			this.user = getLoggedUser();
			//Initialize data tables
			updateCompanyProducts();
			updatePaymentRequests();
		} catch (Exception e) {
			logger.error("An error has ocurred while initializing the CompanyAccountManagerMB.", e);
			showStartupMbExceptionMessage();
		}
	}
	
	/**
	 * Updates the content of the list that 
	 * feeds the company products data table.
	 */
	public void updateCompanyProducts() {
		this.companyProducts = null;
	}
	
	/**
	 * Updates the content of the list that
	 * feeds the company payment requests data table.
	 */
	public void updatePaymentRequests() {
		this.paymentRequests = null;
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
		} catch (Exception e) {
			final String ERROR_MSG = String.format("An error has ocurred while ending product validity for id: %d", PRODUCT_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Raises a protest against the specified payment request.
	 * @param paymentRequest Payment Request to protest against.
	 */
	public void paymentRequestProtest(PaymentRequest paymentRequest) {
		final long P_REQUEST_ID = ((paymentRequest != null) ? paymentRequest.getPaymentRequestId() : -1L);
		final String INFO_MSG = String.format("The company has protested against the payment request id: %d.", P_REQUEST_ID);
		try {
			logger.info(INFO_MSG);
			
		} catch (Exception e) {
			final String ERROR_MSG = String.
					format("An error has ocurred while the company protested against the payment request id: %d", P_REQUEST_ID);
			logger.error(ERROR_MSG, e);
			showGeneralExceptionMessage();
		}
	}
	
	/**
	 * Downloads the buy proof files from the specified object.
	 * @param buyProof
	 * @return
	 */
	public StreamedContent downloadBuyProofObject(PaymentRequest paymentRequest) {
		logger.info("Downloading buy proof file...");
		StreamedContent streamedContent = null;
		BuyProof buyProof = null;
		try {
			if(paymentRequest != null && paymentRequest.getBuyProofs() != null) {
				int buyProofsLength = paymentRequest.getBuyProofs().size();
				List<BuyProof> buyProofs = paymentRequest.getBuyProofs();
				buyProof = ((buyProofsLength > 0) ? buyProofs.get(buyProofsLength - 1) : null);
				if(buyProof == null) {
					throw new Exception("There is not exists a Buy Proof file.");
				}
			}
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
	
	public List<Product> getCompanyProducts() {
		return companyProducts;
	}
	
	public void setCompanyProducts(List<Product> companyProducts) {
		this.companyProducts = companyProducts;
	}
	
	public List<PaymentRequest> getPaymentRequests() {
		return paymentRequests;
	}
	
	public void setPaymentRequests(List<PaymentRequest> paymentRequests) {
		this.paymentRequests = paymentRequests;
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
