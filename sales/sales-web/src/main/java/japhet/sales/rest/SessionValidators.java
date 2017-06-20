package japhet.sales.rest;

import static japhet.sales.data.QueryParameters.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.log4j.Logger;

import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.UserProductHistorial;
import japhet.sales.rest.model.impl.GuestSession;
import japhet.sales.service.IUserProductHistorialService;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@Path("session_validator")
public class SessionValidators {
	
	@Inject
	private Logger logger;
	
	@EJB
	private IUserProductHistorialService userProductHistorialService;
	
	/**
	 * Validates if a session key is valid and was created within a range of 3 hours.
	 * 
	 * @param sessionKey session key of the redirection to the sales third party site
	 * @param providerId provider id of the third party site
	 * @return true if the session exists in the DB and respects the business rules
	 * @author David
	 */
	@GET
	@Path("/guest_session/{session_key}/{provider_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GuestSession validateGuestSession(@PathParam("session_key") String sessionKey, 
			@PathParam("provider_id") Long providerId) {
		final short HOURS_RANGE_ALLOWED = (short)3;
		final Long P_PROVIDER_ID = ((providerId != null) ? providerId : -1L);
		final String INFO_MSG = String
				.format("Validating guest session: %s provider: %d...", sessionKey, P_PROVIDER_ID);
		GuestSession guestSession = new GuestSession();
		try {
			logger.info(INFO_MSG);
			//Set the parameters for the query
			Map<String, Object> params = new HashMap<>();
			params.put(FINGERPRINT, sessionKey);
			//Query the database to find the fingerprint if exists
			UserProductHistorial userProductHistorial = userProductHistorialService
					.getCompletedHistorialByFingerprint(params);
			guestSession.setSessionKey(sessionKey);
			//Validate if the result is valid
			if(userProductHistorial != null) {
				Company company = userProductHistorial.getProduct().getCompany();
				//Calculate the different of time in hours from its creation to the current millis
				long clickMillis = userProductHistorial.getClickDate().getTime();
				long currentMillis = new Date().getTime();
				long dateDifference = currentMillis - clickMillis;
				short hoursOfDifference = (short) (dateDifference / (60 * 60 * 1000));
				//Verify business rules
				if(!userProductHistorial.getCompleted() 
						&& userProductHistorial.getCompletedDate() == null
						&& userProductHistorial.getUserProductHistorialKey().equals(sessionKey)
						&& company.getCompanyId() == P_PROVIDER_ID
						&& hoursOfDifference <= HOURS_RANGE_ALLOWED) {
					guestSession.setValidSession(true);
				}
			}
		} catch (Exception e) {
			final String ERROR_MSG = String
					.format("An error has occurred while validating guest session: %s provider: %d."
							, sessionKey, P_PROVIDER_ID);
			logger.error(ERROR_MSG, e);
		}
		return guestSession;
	}
}
