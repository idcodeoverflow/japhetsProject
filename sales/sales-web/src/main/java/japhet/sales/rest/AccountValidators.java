package japhet.sales.rest;

import static japhet.sales.data.QueryParameters.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import japhet.sales.catalogs.Statuses;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.model.impl.Status;
import japhet.sales.model.impl.User;
import japhet.sales.rest.util.PathTools;
import japhet.sales.service.IUserService;
import japhet.sales.util.Navigator;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@Path("/validator")
public class AccountValidators {
	
	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IUserService userService;

	//Context
	@Context
	private UriInfo uriInfo;
	
	@Path("validate/{userId}")
	@GET
	public Response validateUserAccount(@PathParam("userId") String userId) {
		final String VALIDATING_INFO = String.format("Verifying user information: %s...", userId);
		final String ACTIVATING_USER = String.format("Activating user: %s...", userId);
		URI uri = null;
		final String BASE_URI = PathTools.substrPerNTokenReverse(uriInfo.getBaseUri().toString(), '/', 3);
		Map<String, Object> params = new HashMap<>();
		try {
			logger.info(VALIDATING_INFO);
			params.put(HASH_KEY, userId);
			User user = userService.getUserByHashKey(params);
			//Verify that everything is fine with the user account
			if(user != null 
					&& !user.getValidatedAccount()
					&& user.getStatus() != null
					&& user.getStatus().getStatusId() == Statuses.DISABLED.getId() 
					&& user.getLastModified().equals(user.getSignUpDate())) {
				//If everything is fine continue with the activation of the user account
				logger.info(ACTIVATING_USER);
				uri = new URI(BASE_URI + Navigator.USER_VALIDATED_URL);
				Status activatedStatus = new Status();
				activatedStatus.setStatusId(Statuses.ACTIVE.getId());
				user.setStatus(activatedStatus);
				user.setValidatedAccount(true);
				user.setLastModified(new Date());
				userService.updateUser(user);
			} else {
				uri = new URI(BASE_URI + Navigator.USER_VALIDATION_FAILED_URL);
			}
		} catch (BusinessServiceException e) {
			final String VALIDATING_EXCEPTION = String
					.format("An error has occurred while activating the user account: %s.", userId);
			logger.error(VALIDATING_EXCEPTION, e);
		} catch (Exception e ) {
			final String VALIDATING_EXCEPTION = String
					.format("An error has occurred with the activation service: %s.", userId);
			logger.error(VALIDATING_EXCEPTION, e);
		} 
		return Response.temporaryRedirect(uri).build();
	}
}
