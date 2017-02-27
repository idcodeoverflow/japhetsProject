package japhet.sales.service.impl;

import static japhet.sales.data.QueryParameters.*;
import static japhet.sales.catalogs.Statuses.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.catalogs.Statuses;
import japhet.sales.data.impl.UserProductHistorialDAO;
import japhet.sales.dto.UserBudget;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidBuyProofException;
import japhet.sales.model.impl.BuyProof;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.User;
import japhet.sales.model.impl.UserProductHistorial;
import japhet.sales.service.IUserProductHistorialService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class UserProductHistorialService implements IUserProductHistorialService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2632136770021767074L;

	@Inject
	private Logger logger;
	
	@EJB
	private UserProductHistorialDAO userProductHistorialDAO;

	@Override
	public List<UserProductHistorial> getHistorialByUser(Map<String, Object> params)   
			throws BusinessServiceException {
		logger.info("Getting historial by user id...");
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getHistorialByUser(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting the historial for the userId.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorials;
	}
	
	@Override
	public List<UserProductHistorial> getCompletedHistorialByUser(Map<String, Object> params)   
			throws BusinessServiceException {
		logger.info("Getting completed historial by user id...");
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getCompletedHistorialByUser(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting the completed historial for the userId.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorials;
	}
	
	@Override
	public List<UserProductHistorial> getCompletedHistorialByUserAndStatus(Map<String, Object> params) 
			throws BusinessServiceException{
		logger.info("Getting completed historial by user id and status...");
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getCompletedHistorialByUserAndStatus(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting the completed historial for the userId and status.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorials;
	}

	@Override
	public UserProductHistorial getCompletedHistorialByFingerprint(Map<String, Object> params) 
			throws BusinessServiceException {
		logger.info("Getting completed historial by fingerprint...");
		UserProductHistorial userProductHistorial = null;
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getCompletedHistorialByFingerprint(params);
			//Obtain first user product historial
			if(userProductHistorials != null && !userProductHistorials.isEmpty()) {
				userProductHistorial = userProductHistorials.get(0);
			}
		} catch (Exception e) {
			final String errorMsg = "Error while getting the completed historial by fingerprint.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorial;
	}

	@Override
	public List<UserProductHistorial> getHistorialByProduct(Map<String, Object> params)   
			throws BusinessServiceException {
		logger.info("Getting historial by product id...");
		List<UserProductHistorial> userProductHistorials = null;
		try {
			userProductHistorials = userProductHistorialDAO.getHistorialByProduct(params);
		} catch (Exception e) {
			final String errorMsg = "Error while getting the historial by the productId.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorials;
	}

	@Override
	public void insertProductHistorial(UserProductHistorial userProductHistorial)   
			throws BusinessServiceException {
		logger.info("Inserting the product historial related to the user...");
		try {
			userProductHistorialDAO.insert(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error inserting the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void updateProductHistorial(UserProductHistorial userProductHistorial)   
			throws BusinessServiceException {
		logger.info("Updating the product historial related to the user...");
		try {
			userProductHistorialDAO.update(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error updating the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public void deleteProductHistorial(UserProductHistorial userProductHistorial)   
			throws BusinessServiceException {
		logger.info("Deleting the product historial related to the user...");
		try {
			userProductHistorialDAO.delete(userProductHistorial);
		} catch (Exception e) {
			final String errorMsg = "Error deleting the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
	}

	@Override
	public UserProductHistorial getProductHistorialById(Long historialId)   
			throws BusinessServiceException {
		logger.info("Getting the product historial related to the user...");
		UserProductHistorial userProductHistorial = null;
		try {
			userProductHistorial = userProductHistorialDAO.select(historialId);
		} catch (Exception e) {
			final String errorMsg = "Error getting the product historial related to the user.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return userProductHistorial;
	}
	
	@Override
	public void verifyTotalAmounts(BuyProof buyProof) 
			throws InvalidBuyProofException {
		Map<String, Object> params = new HashMap<>();
		params.put(FINGERPRINT, buyProof.getUserProductHistorialKey());
		UserProductHistorial userPrdctHist;
		try {
			userPrdctHist = getCompletedHistorialByFingerprint(params);
		} catch (BusinessServiceException e) {
			final String FATAL_MSG = "Error at query execution.";
			logger.fatal(FATAL_MSG, e);
			throw new InvalidBuyProofException(FATAL_MSG, e);
		}
		//Validate if the total amount is the same
		if(buyProof == null || userPrdctHist == null 
				|| Math.abs(buyProof.getTotal() - userPrdctHist.getTotal()) > 0.000001) {
			StringBuilder strBldrErr = new StringBuilder("Invalid user prdct Historial ");
			strBldrErr.append(buyProof.getTotal());
			strBldrErr.append(" mismatches ");
			strBldrErr.append(userPrdctHist.getTotal());
			logger.error(strBldrErr);
			throw new InvalidBuyProofException(strBldrErr.toString());
		}
	}
	
	@Override
	public UserBudget obtainReadyOnWaitPaybackAmounts(User user) 
			throws BusinessServiceException {
		Map<String, Object> params = new HashMap<>();
		Company company = null;
		UserBudget paybackAmounts = null;
		double paybackReadyAmount = 0.0;
		double paybackOnWaitAmount = 0.0;
		params.put(USER_ID, user.getUserId());
		params.put(STATUS_ID, VALIDATED.getId());
		//Obtain user product historials
		List<UserProductHistorial> userProductHistorials = getCompletedHistorialByUserAndStatus(params);
		//Obtain the number of days required to free the user budget
		short companyProtectionDays = 0;
		Date today = new Date();
		Date transactionDate = new Date();
		Date approvementDate = new Date();
		Calendar calendar = Calendar.getInstance();
		if(userProductHistorials != null) {
			//Calculate the total user budget
			for(UserProductHistorial userProductHistorial : userProductHistorials) {
				//Set the company protection days from the product
				company = userProductHistorial.getProduct().getCompany();
				companyProtectionDays = company.getDaysNumberToApprove();
				
				if(userProductHistorial.getCompletedDate() != null) {
					transactionDate = userProductHistorial.getCompletedDate();
				}
				calendar.setTime(transactionDate);
				//Increase the number of days that are required to allow a deposit
				calendar.add(Calendar.DATE, companyProtectionDays);
				approvementDate = calendar.getTime();
				if(today.after(approvementDate)) {
					paybackReadyAmount += userProductHistorial.getPaybackAmount();
				} else {
					paybackOnWaitAmount += userProductHistorial.getPaybackAmount();
				}
			}
		}
		paybackAmounts = new UserBudget(paybackReadyAmount, paybackOnWaitAmount);
		return paybackAmounts;
	}

	@Override
	public List<BuyProof> obtainBuyProofsReadyToPay(User user) 
			throws BusinessServiceException {
		Map<String, Object> params = new HashMap<>();
		Company company = null;
		List<BuyProof> buyProofs = new ArrayList<>();
		params.put(USER_ID, user.getUserId());
		params.put(STATUS_ID, VALIDATED.getId());
		//Obtain user product historials
		List<UserProductHistorial> userProductHistorials = getCompletedHistorialByUserAndStatus(params);
		//Obtain the number of days required to free the user budget
		short companyProtectionDays = 0;
		Date today = new Date();
		Date transactionDate = new Date();
		Date approvementDate = new Date();
		Calendar calendar = Calendar.getInstance();
		if(userProductHistorials != null) {
			//Calculate the total user budget
			for(UserProductHistorial userProductHistorial : userProductHistorials) {
				//Set the company protection days from the product
				company = userProductHistorial.getProduct().getCompany();
				companyProtectionDays = company.getDaysNumberToApprove();
				
				if(userProductHistorial.getCompletedDate() != null) {
					transactionDate = userProductHistorial.getCompletedDate();
				}
				calendar.setTime(transactionDate);
				//Increase the number of days that are required to allow a deposit
				calendar.add(Calendar.DATE, companyProtectionDays);
				approvementDate = calendar.getTime();
				if(today.after(approvementDate)) {
					for(BuyProof buyProof : userProductHistorial.getBuyProofs()) {
						if(buyProof.getStatus().getStatusId() == Statuses.VALIDATED.getId()) {
							buyProofs.add(buyProof);
						}
					}
				} 
			}
		}
		return buyProofs;
	}
}
