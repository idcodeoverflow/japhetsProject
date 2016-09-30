package japhet.sales.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.model.impl.BuyProof;

@Local
public interface IBuyProof extends Serializable {

	public BuyProof selectBuyProof(Long buyProofId);
	
	public List<BuyProof> getBuyProofsByUser(Map<String, Object> params);
	
	public List<BuyProof> getBuyProofsByProduct(Map<String, Object> params);
	
	public void updateBuyProof(BuyProof buyProof);
	
	public void deleteBuyProof(BuyProof buyProof);
	
	public void insertBuyProof(BuyProof buyProof);
	
}
