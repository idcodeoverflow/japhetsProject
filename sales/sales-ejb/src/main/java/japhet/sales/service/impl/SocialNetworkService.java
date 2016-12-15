package japhet.sales.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import japhet.sales.data.impl.SocialNetworkDAO;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.SocialNetwork;
import japhet.sales.service.ISocialNetworkService;

import org.apache.log4j.Logger;

@LocalBean
@Stateless
public class SocialNetworkService implements ISocialNetworkService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1791506380978459987L;

	@Inject
	private Logger logger;
	
	@EJB
	private SocialNetworkDAO socialNetworkDAO;
	
	@Override
	public List<SocialNetwork> getAllSocialNetworks()   
			throws BusinessServiceException {
		logger.info("Obtaining all social networks...");
		List<SocialNetwork> socialNetworks = null;
		try {
			socialNetworks = socialNetworkDAO.getAllSocialNetworks();
		} catch (Exception e) {
			final String errorMsg = "Error while getting all Social Networks.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return socialNetworks;
	}

	@Override
	public List<SocialNetwork> getAllAvailableSocialNetworks()   
			throws BusinessServiceException {
		logger.info("Obtaining all available social networks...");
		List<SocialNetwork> socialNetworks = null;
		try {
			socialNetworks = socialNetworkDAO.getAllAvailableSocialNetworks();
		} catch (Exception e) {
			final String errorMsg = "Error while getting all available Social Networks.";
			logger.fatal(errorMsg, e);
			throw new BusinessServiceException(errorMsg, e);
		}
		return socialNetworks;
	}
}
