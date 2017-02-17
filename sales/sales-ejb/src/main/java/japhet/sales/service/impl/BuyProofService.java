package japhet.sales.service.impl;

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
		logger.info("Getting buy proof: " + buyProofId + "...");
		BuyProof buyProof = null;
		try {
			buyProof = buyProofDAO.select(buyProofId);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to get the buy proof.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
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
			final String errorMsg = "Error while trying to get the buy proofs by user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
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
			final String errorMsg = "Error while trying to get the buy proofs by user and status.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return buyProofs;
	}

	@Override
	public void updateBuyProof(BuyProof buyProof)   
			throws BusinessServiceException {
		logger.info("Updating buy proof...");
		try {
			buyProofDAO.update(buyProof);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to update buy proof.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void deleteBuyProof(BuyProof buyProof)   
			throws BusinessServiceException {
		logger.info("Deleting buy proof...");
		try {
			buyProofDAO.delete(buyProof);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to delete buy proof.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void insertBuyProof(BuyProof buyProof)   
			throws BusinessServiceException {
		logger.info("Inserting buy proof...");
		try {
			buyProofDAO.insert(buyProof);
		} catch (Exception e) {
			final String errorMsg = "Error while trying to insert buy proof.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

}
