package japhet.sales.data.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.data.GenericDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.model.impl.SocialNetwork;

@Stateless
public class SocialNetworkDAO extends GenericDAO<SocialNetwork, Short>{
	
	@Inject
	private Logger logger;
	
	public SocialNetworkDAO() {
		super(SocialNetwork.class, Short.class);
	}
	
	public List<SocialNetwork> getAllSocialNetworks() 
			throws DataLayerException {
		List<SocialNetwork> socialNetworks = null;
		try {
			logger.info("Obtaining all social networks...");
			socialNetworks = executeQuery(GET_ALL_SOCIAL_NETWORKS, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining all social networks.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return socialNetworks;
	}
	
	public List<SocialNetwork> getAllAvailableSocialNetworks() 
			throws DataLayerException {
		List<SocialNetwork> socialNetworks = null;
		try {
			logger.info("Obtaining all available social networks...");
			socialNetworks = executeQuery(GET_ALL_AVAILABLE_SOCIAL_NETWORKS, null);
		} catch (Exception e) {
			final String ERROR_MSG = "Error while obtaining all available social networks.";
			logger.fatal(ERROR_MSG, e);
			throw new DataLayerException(ERROR_MSG, e);
		}
		return socialNetworks;
	}
	
}
