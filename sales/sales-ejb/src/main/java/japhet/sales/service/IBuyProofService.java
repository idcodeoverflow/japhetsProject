package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.BuyProof;

@Local
public interface IBuyProofService extends Serializable {

	public BuyProof selectBuyProof(Long buyProofId) 
			throws BusinessServiceException;
	
	public List<BuyProof> getBuyProofsByUser(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public List<BuyProof> getBuyProofsByUserAndStatus(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public List<BuyProof> getBuyProofsByCompanyAndStatus(Map<String, Object> params) 
			throws BusinessServiceException;
	
	public void updateBuyProof(BuyProof buyProof) 
			throws BusinessServiceException;
	
	public void deleteBuyProof(BuyProof buyProof) 
			throws BusinessServiceException;
	
	public void insertBuyProof(BuyProof buyProof) 
			throws BusinessServiceException;
	
	public short updateBuyProofsBatch(Map<String, Object> params) 
			throws BusinessServiceException;
	
}
