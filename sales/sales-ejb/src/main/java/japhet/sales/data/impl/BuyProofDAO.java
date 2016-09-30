package japhet.sales.data.impl;

import static japhet.sales.data.QueryNames.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.BuyProof;

@Stateless
public class BuyProofDAO extends GenericDAO<BuyProof, Long> {

	@Inject
	private Logger logger;
	
	public BuyProofDAO() {
		super(BuyProof.class, Long.class);
	}
	
	public List<BuyProof> getBuyProofsByUser(Map<String, Object> params) {
		List<BuyProof> buyProofs = null;
		logger.info("Obtaining buy proofs by user...");
		try {
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_USER, params);
		} catch (Exception e) {
			logger.info("Error whule obtaining buy proofs by user.\n" + e.getStackTrace());
		}
		return buyProofs;
	}
	
	public List<BuyProof> getBuyProofsByProduct(Map<String, Object> params) {
		List<BuyProof> buyProofs = null;
		logger.info("Obtaining buy proofs by product...");
		try {
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_PRODUCT, params);
		} catch (Exception e) {
			logger.info("Error whule obtaining buy proofs by product.\n" + e.getStackTrace());
		}
		return buyProofs;
	}
	
}
