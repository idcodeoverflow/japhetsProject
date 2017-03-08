package japhet.sales.service.impl;

import static japhet.sales.data.QueryParameters.*;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.BuyProofDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.BuyProof;
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
		logger.info("Getting buy proofs by user...");
		List<BuyProof> buyProofs = null;
		try {
			buyProofs = buyProofDAO.getBuyProofsByUser(params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to get the buy proofs by user.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	@Override
	public List<BuyProof> getBuyProofsByUserAndStatus(Map<String, Object> params) 
			throws BusinessServiceException {
		logger.info("Getting buy proofs by user and status...");
		List<BuyProof> buyProofs = null;
		try {
			buyProofs = buyProofDAO.getBuyProofsByUserAndStatus(params);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to get the buy proofs by user and status.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return buyProofs;
	}
	
	@Override
	public List<BuyProof> getBuyProofsByCompany(Map<String, Object> params) 
			throws BusinessServiceException {
		List<BuyProof> buyProofs = null;
		final long P_BPROOF_ID = ((params != null && params.get(COMPANY_ID) != null) ? (Long)params.get(COMPANY_ID) : -1L);
		final String MSG_INFO = String.format("Getting buy proofs by user and status: %d...", P_BPROOF_ID);
		try {
			logger.info(MSG_INFO);
			buyProofs = buyProofDAO.getBuyProofsByCompany(params);
		} catch (Exception e) {
			final String ERROR_MSG = String.format("Error while trying to get the buy proofs by user: %d and status.", P_BPROOF_ID);
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
			final String ERROR_MSG = String.format("Error while trying to update buy proof.", P_BPROOF_ID);
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
		final String INFO_MSG = "Inserting buy proof...";
		try {
			logger.info(INFO_MSG);
			buyProofDAO.insert(buyProof);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while trying to insert buy proof.";
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
