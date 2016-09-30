package japhet.sales.data.impl;

import static japhet.sales.data.QueryNames.*;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.SocialNetwork;

@Stateless
public class SocialNetworkDAO extends GenericDAO<SocialNetwork, Short>{
	
	@Inject
	private Logger logger;
	
	public SocialNetworkDAO() {
		super(SocialNetwork.class, Short.class);
	}
	
	public List<SocialNetwork> getAllSocialNetworks() {
		logger.info("Obtaining all social networks...");
		List<SocialNetwork> socialNetworks = null;
		try {
			socialNetworks = executeQuery(GET_ALL_SOCIAL_NETWORKS, null);
		} catch (Exception e) {
			logger.severe("Error while obtaining all social networks. \n" + 
					e.getStackTrace());
		}
		return socialNetworks;
	}
	
	public List<SocialNetwork> getAllAvailableSocialNetworks() {
		logger.info("Obtaining all available social networks...");
		List<SocialNetwork> socialNetworks = null;
		try {
			socialNetworks = executeQuery(GET_ALL_AVAILABLE_SOCIAL_NETWORKS, null);
		} catch (Exception e) {
			logger.severe("Error while obtaining all available social networks. \n" + 
					e.getStackTrace());
		}
		return socialNetworks;
	}
	
}
