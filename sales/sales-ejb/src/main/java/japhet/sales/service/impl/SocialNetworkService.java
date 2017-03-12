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
		List<SocialNetwork> socialNetworks = null;
		try {
			logger.info("Obtaining all social networks...");
			socialNetworks = socialNetworkDAO.getAllSocialNetworks();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all Social Networks.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return socialNetworks;
	}

	@Override
	public List<SocialNetwork> getAllAvailableSocialNetworks()   
			throws BusinessServiceException {
		List<SocialNetwork> socialNetworks = null;
		try {
			logger.info("Obtaining all available social networks...");
			socialNetworks = socialNetworkDAO.getAllAvailableSocialNetworks();
		} catch (Exception e) {
			final String ERROR_MSG = "Error while getting all available Social Networks.";
			logger.fatal(ERROR_MSG, e);
			throw new BusinessServiceException(ERROR_MSG, e);
		}
		return socialNetworks;
	}
}
