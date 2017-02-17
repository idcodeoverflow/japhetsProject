package japhet.sales.data.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.BuyProof;

@Stateless
public class BuyProofDAO extends GenericDAO<BuyProof, Long> {

	@Inject
	private Logger logger;
	
	public BuyProofDAO() {
		super(BuyProof.class, Long.class);
	}
	
	public List<BuyProof> getBuyProofsByUser(Map<String, Object> params) 
			throws DataLayerException {
		List<BuyProof> buyProofs = null;
		logger.info("Obtaining buy proofs by user...");
		try {
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_USER, params);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining buy proofs by user.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return buyProofs;
	}
			
	public List<BuyProof> getBuyProofsByUserAndStatus(Map<String, Object> params) 
			throws DataLayerException {
		List<BuyProof> buyProofs = null;
		logger.info("Obtaining buy proofs by user and status...");
		try {
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_USER_N_STATUS, params);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining buy proofs by user and status.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return buyProofs;
	}
}
