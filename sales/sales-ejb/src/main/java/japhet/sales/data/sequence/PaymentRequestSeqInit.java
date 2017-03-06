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
	
	private PaymentRequestSeqInit() {
		super(PaymentRequest.class, Long.class);
	}
	
	@PostConstruct
	private void init() {
		logger.info("Initializing payment request sequence...");
		final String INFO_MSG = "The payment request sequence initializing at: %d";
		try {
			Long initialPk = (Long)em.createQuery("SELECT MAX(pr.paymentRequestId) FROM PaymentRequest pr").getSingleResult();
			initialPk = ((initialPk != null) ? initialPk + 1 : 1);
			logger.info(String.format(INFO_MSG, initialPk));
		} catch (Exception e) {
			logger.fatal("An error has ocurred while initializing the payment request sequence.", e);
		}
	}
}
