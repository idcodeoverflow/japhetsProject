package japhet.sales.service.impl;

import static japhet.sales.data.QueryParameters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.catalogs.Statuses;
import japhet.sales.data.impl.BuyProofDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.Status;
import japhet.sales.service.IBuyProofService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class BuyProofService implements IBuyProofService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1504523881070926512L;

	@Inject
	private Logger logger;
	
	@EJB
	private BuyProofDAO buyProofDAO;
	
	@Override
	public BuyProof selectBuyProof(Long buyProofId)  
			throws BusinessServiceException {
		BuyProof buyProof = null;
		final long B_PROOF_ID = ((buyProofId != null) ? buyProofId : -1L);
		final String INFO_MSG = String.format("Getting buy proof: %d...", B_PROOF_ID);
		try {
			logger.info(INFO_MSG);
			buyProof = buyProofDAO.select(buyProofId);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while trying to get the buy proof: %d.", B_PROOF_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProof;
	}

	@Override
	public List<BuyProof> getBuyProofsByUser(Map<String, Object> params)   
			throws BusinessServiceException {
		final long P_USER_ID = ((params != null && params.get(USER_ID) != null) ? (Long)params.get(USER_ID) : -1L);
		final String INFO_MSG = String.format("Getting buy proofs by user: %d...", P_USER_ID);
		List<BuyProof> buyProofs = null;
		try {
			logger.info(INFO_MSG);
			buyProofs = buyProofDAO.getBuyProofsByUser(params);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while trying to get the buy proofs by user: %d.", P_USER_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	@Override
	public List<BuyProof> getBuyProofsByUserAndStatus(Map<String, Object> params) 
			throws BusinessServiceException {
		final long P_USER_ID = ((params != null && params.get(USER_ID) != null) ? (Long)params.get(USER_ID) : -1L);
		final short P_STATUS_ID = ((params != null && params.get(STATUS_ID) != null) ? (Short)params.get(STATUS_ID) : -1);
		final String INFO_MSG = String.format("Getting buy proofs by user: %d and status: %d...", P_USER_ID, P_STATUS_ID);
		List<BuyProof> buyProofs = null;
		try {
			logger.info(INFO_MSG);
			buyProofs = buyProofDAO.getBuyProofsByUserAndStatus(params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("Error while trying to get the buy proofs by user: %d and status: %d.", P_USER_ID, P_STATUS_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BuyProof> getBuyProofsByStatus(Map<String, Object> params) 
			throws BusinessServiceException {
		final List<Status> statusListParam = ((params != null 
				&& params.get(STATUS_ID) != null) ? (ArrayList<Status>)params.get(STATUS_ID) : new ArrayList<Status>());
		StringBuilder P_STATUSES = new StringBuilder();
		for(Status status : statusListParam) {
			P_STATUSES.append(status.getStatusId());
			P_STATUSES.append(",");
		}
		final String INFO_MSG = String.format("Getting buy proofs status: %s...", P_STATUSES);
		List<BuyProof> buyProofs = null;
		try {
			logger.info(INFO_MSG);
			buyProofs = buyProofDAO.getBuyProofsByStatus(params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("Error while trying to get the buy proofs by status: %s.", P_STATUSES);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	@Override
	public List<BuyProof> getBuyProofsByCompanyAndStatus(Map<String, Object> params) 
			throws BusinessServiceException {
		List<BuyProof> buyProofs = null;
		final short CRAISED_ID = Statuses.CASE_RAISED.getId();
		final short ONREQ_ID = Statuses.ON_PAYMENT_REQUEST.getId();
		final long P_COMP_ID = ((params != null && params.get(COMPANY_ID) != null) ? (Long)params.get(COMPANY_ID) : -1L);
		final String MSG_INFO = String.format("Getting buy proofs by company: %d and status: %d, %d...", P_COMP_ID, CRAISED_ID, ONREQ_ID);
		try {
			logger.info(MSG_INFO);
			buyProofs = buyProofDAO.getBuyProofsByCompanyAndStatus(params);
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("Error while trying to get the buy proofs by company: %d and status: %d, %d...", P_COMP_ID, CRAISED_ID, ONREQ_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProofs;
	}

	@Override
	public void updateBuyProof(BuyProof buyProof)   
			throws BusinessServiceException {
		final long P_BPROOF_ID = ((buyProof != null 
				&& buyProof.getBuyProofId() != null) ? buyProof.getBuyProofId() : -1L);
		final String INFO_MSG = String.format("Updating buy proof: %d...", P_BPROOF_ID);
		try {
			logger.info(INFO_MSG);
			buyProofDAO.update(buyProof);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while trying to update buy proof: %d.", P_BPROOF_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public void deleteBuyProof(BuyProof buyProof)   
			throws BusinessServiceException {
		final long P_BPROOF_ID = ((buyProof != null 
				&& buyProof.getBuyProofId() != null) ? buyProof.getBuyProofId() : -1L);
		final String INFO_MSG = String.format("Deleting buy proof: %d...", P_BPROOF_ID);
		try {
			logger.info(INFO_MSG);
			buyProofDAO.delete(buyProof);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while trying to delete buy proof: %d.", P_BPROOF_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public void insertBuyProof(BuyProof buyProof)   
			throws BusinessServiceException {
		final long P_BPROOF_ID = ((buyProof != null 
				&& buyProof.getBuyProofId() != null) ? buyProof.getBuyProofId() : -1L);
		final String INFO_MSG = String.format("Inserting buy proof: %d...", P_BPROOF_ID);
		try {
			logger.info(INFO_MSG);
			buyProofDAO.insert(buyProof);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while trying to insert buy proof: %d.", P_BPROOF_ID);
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
	}

	@Override
	public short updateBuyProofsBatch(Map<String, Object> params) 
			throws BusinessServiceException {
		short rowsUpdated = 0;
		final String INFO_MSG = "Updating a batch of buy proofs...";
		try {
			logger.info(INFO_MSG);
			rowsUpdated = buyProofDAO.updateBuyProofsBatch(params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while updating a batch of buy proofs....";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return rowsUpdated;
	}
}
