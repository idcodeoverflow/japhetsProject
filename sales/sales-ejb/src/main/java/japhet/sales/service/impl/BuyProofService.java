package japhet.sales.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.BuyProofDAO;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.service.IBuyProof;

@LocalBean
@Stateless
public class BuyProofService implements IBuyProof {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1504523881070926512L;

	@Inject
	private Logger logger;
	
	@EJB
	private BuyProofDAO buyProofDAO;
	
	@Override
	public BuyProof selectBuyProof(Long buyProofId) {
		logger.info("Getting buy proof: " + buyProofId + "...");
		BuyProof buyProof = null;
		try {
			buyProof = buyProofDAO.select(buyProofId);
		} catch (Exception e) {
			logger.severe("Error while trying to get the buy proof.\n" 
					+ e.getStackTrace());
		}
		return buyProof;
	}

	@Override
	public List<BuyProof> getBuyProofsByUser(Map<String, Object> params) {
		logger.info("Getting buy proofs by user...");
		return buyProofDAO.getBuyProofsByUser(params);
	}

	@Override
	public List<BuyProof> getBuyProofsByProduct(Map<String, Object> params) {
		logger.info("Getting buy proofs by product...");
		return buyProofDAO.getBuyProofsByProduct(params);
	}

	@Override
	public void updateBuyProof(BuyProof buyProof) {
		logger.info("Updating buy proof...");
		try {
			buyProofDAO.update(buyProof);
		} catch (Exception e) {
			logger.severe("Error while trying to update buy proof.\n" 
					+ e.getStackTrace());
		}
	}

	@Override
	public void deleteBuyProof(BuyProof buyProof) {
		logger.info("Deleting buy proof...");
		try {
			buyProofDAO.delete(buyProof);
		} catch (Exception e) {
			logger.severe("Error while trying to delete buy proof.\n" 
					+ e.getStackTrace());
		}
	}

	@Override
	public void insertBuyProof(BuyProof buyProof) {
		logger.info("Inserting buy proof...");
		try {
			buyProofDAO.insert(buyProof);
		} catch (Exception e) {
			logger.severe("Error while trying to insert buy proof.\n" 
					+ e.getStackTrace());
		}
	}

}
