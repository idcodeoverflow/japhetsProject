package japhet.sales.data.sequence;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.PaymentRequest;

@Startup
@Singleton
public class PaymentRequestSeqInit extends GenericDAO<PaymentRequest, Long> {

	@Inject
	private Logger logger;
	
	private Long initialPk;
	
	private PaymentRequestSeqInit() {
		super(PaymentRequest.class, Long.class);
	}
	
	@PostConstruct
	private void init() {
		final String INFO_MSG = "The payment request sequence initializing at: %d";
		try {
			logger.info("Initializing payment request sequence...");
			initialPk = em.createQuery("SELECT MAX(pr.paymentRequestId) FROM PaymentRequest pr", Long.class)
					.getSingleResult();
			initialPk = ((initialPk != null) ? initialPk + 1 : 1);
			PaymentRequest.setPaymentRequestSequence(initialPk);
			logger.info(String.format(INFO_MSG, initialPk));
		} catch (Exception e) {
			logger.fatal("An error has ocurred while initializing the payment request sequence.", e);
		}
	}

	/**
	 * @return the initialPk
	 */
	public Long getInitialPk() {
		return initialPk;
	}

	/**
	 * @param initialPk the initialPk to set
	 */
	public void setInitialPk(Long initialPk) {
		this.initialPk = initialPk;
	}
}
