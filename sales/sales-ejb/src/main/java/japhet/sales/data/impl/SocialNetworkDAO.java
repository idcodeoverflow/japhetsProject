package japhet.sales.data.impl;

import static japhet.sales.data.QueryNames.*;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.SocialNetwork;

import org.apache.log4j.Logger;

@Stateless
public class SocialNetworkDAO extends GenericDAO<SocialNetwork, Short>{
	
	@Inject
	private Logger logger;
	
	public SocialNetworkDAO() {
		super(SocialNetwork.class, Short.class);
	}
	
	public List<SocialNetwork> getAllSocialNetworks() 
			throws DataLayerException {
		logger.info("Obtaining all social networks...");
		List<SocialNetwork> socialNetworks = null;
		try {
			socialNetworks = executeQuery(GET_ALL_SOCIAL_NETWORKS, null);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining all social networks.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return socialNetworks;
	}
	
	public List<SocialNetwork> getAllAvailableSocialNetworks() 
			throws DataLayerException {
		logger.info("Obtaining all available social networks...");
		List<SocialNetwork> socialNetworks = null;
		try {
			socialNetworks = executeQuery(GET_ALL_AVAILABLE_SOCIAL_NETWORKS, null);
		} catch (Exception e) {
			final String errorMsg = "Error while obtaining all available social networks.";
			logger.fatal(errorMsg, e);
			throw new DataLayerException(errorMsg, e);
		}
		return socialNetworks;
	}
	
}
