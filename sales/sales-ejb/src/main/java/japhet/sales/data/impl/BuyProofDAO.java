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
			final String ERROR_MSG = "Error while obtaining buy proofs by user.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
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
			final String ERROR_MSG = "Error while obtaining buy proofs by user and status.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	public short updateBuyProofsBatch(Map<String, Object> params) 
			throws DataLayerException {
		StringBuilder buyProofIds = new StringBuilder();
		short rowsUpdated = 0;
		@SuppressWarnings("unchecked")
		List<BuyProof> buyProofs = (List<BuyProof>)params.get(BUY_PROOFS_TO_UPDATE);
		if(buyProofs != null) {
			for(BuyProof buyProof : buyProofs) {
				buyProofIds.append(buyProof.getBuyProofId());
				buyProofIds.append(",");
			}
		}
		final String INFO_MSG = String.
				format("Updating buy proofs which matches these ids: %s...", 
						buyProofIds.toString());
		logger.info(INFO_MSG);
		try {
			rowsUpdated = (short) executeUpdate(UPDATE_BUY_PROOFS_BATCH, params);
		} catch (Exception e) {
			final String ERROR_MSG = String.
					format("Error while updating the buy proofs which matches these ids: %s...", 
							buyProofIds.toString());
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return rowsUpdated;
	}
}
