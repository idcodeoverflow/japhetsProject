package japhet.sales.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Statuses;
import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.Status;

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
		final long P_USER_ID = ((params != null && params.get(USER_ID) != null) ? (Long)params.get(USER_ID) : -1L);
		final String INFO_MSG = String.format("Obtaining buy proofs by user: %d...", P_USER_ID);
		try {
			logger.info(INFO_MSG);
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_USER, params);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while obtaining buy proofs by user: %d.", P_USER_ID);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return buyProofs;
	}
			
	public List<BuyProof> getBuyProofsByUserAndStatus(Map<String, Object> params) 
			throws DataLayerException {
		List<BuyProof> buyProofs = null;
		final long P_USER_ID = ((params != null && params.get(USER_ID) != null) ? (Long)params.get(USER_ID) : -1L);
		final short P_STATUS_ID = ((params != null && params.get(STATUS_ID) != null) ? (Short)params.get(STATUS_ID) : -1);
		final String INFO_MSG = String.format("Obtaining buy proofs by user: %d and status: %d...", P_USER_ID, P_STATUS_ID);
		try {
			logger.info(INFO_MSG);
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_USER_N_STATUS, params);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while obtaining buy proofs by user: %d and status: %d.", P_USER_ID, P_STATUS_ID);
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
	
	@SuppressWarnings("unchecked")
	public List<BuyProof> getBuyProofsByStatus(Map<String, Object> params) 
			throws DataLayerException {
		List<BuyProof> buyProofs = null;
		final List<Status> statusListParam = ((params != null 
				&& params.get(STATUS_ID) != null) ? (ArrayList<Status>)params.get(STATUS_ID) : new ArrayList<Status>());
		StringBuilder P_STATUSES = new StringBuilder();
		for(Status status : statusListParam) {
			P_STATUSES.append(status.getStatusId());
			P_STATUSES.append(",");
		}
		final String INFO_MSG = String.format("Obtaining buy proofs by Status: %s...", P_STATUSES.toString());
		try {
			logger.info(INFO_MSG);
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_STATUS, params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("Error while obtaining buy proofs by Status: %s.", P_STATUSES.toString());
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	public List<BuyProof> getBuyProofsByCompanyAndStatus(Map<String, Object> params) 
			throws DataLayerException {
		List<BuyProof> buyProofs = null;
		final short CRAISED_ID = Statuses.CASE_RAISED.getId();
		final short ONREQ_ID = Statuses.ON_PAYMENT_REQUEST.getId();
		final long P_USER_ID = ((params != null && params.get(COMPANY_ID) != null) ? (Long)params.get(COMPANY_ID) : -1L);
		final String INFO_MSG = String.format("Obtaining buy proofs by Company: %d and Status: %d, %d...", P_USER_ID, CRAISED_ID, ONREQ_ID);
		try {
			logger.info(INFO_MSG);
			buyProofs = executeQuery(GET_BUY_PROOFS_BY_COMPANY_N_STATUS, params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("Error while obtaining buy proofs by Company: %d and Status: %d, %d...", P_USER_ID, CRAISED_ID, ONREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return buyProofs;
	}
}
