package japhet.sales.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import japhet.sales.data.QueryParameters;
import japhet.sales.data.StoredProcedureParameters;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.User;
import japhet.sales.rest.RESTParameters;
import japhet.sales.service.ICompanyService;
import japhet.sales.util.Navigator;

public abstract class GenericMB extends GenericFacesMessager
	implements Serializable, Navigator, QueryParameters, 
		StoredProcedureParameters, RESTParameters {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -5589709030464138421L;
	
	protected String CURRENT_LOCALE = "";
	
	@Inject
	protected Logger logger;
	
	@EJB
	protected IInternationalizationService internationalizationService;
	
	@EJB
	protected ICompanyService companyService;
	
	/**
	 * Gets the current instance from the Faces Context.
	 */
	@Override
	protected FacesContext getCurrentFacesInstance() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * @return Current instance of the External Faces Context.
	 */
	protected ExternalContext getExternalContext() {
		return getCurrentFacesInstance().getExternalContext();
	}
	
	/**
	 * Looks for a parameter name into the Faces External
	 * Context and returns it if exists.
	 * @param name Parameter name to search.
	 * @return Request parameter if exists.
	 */
	protected String getRequestParam(String name) {
		return getCurrentFacesInstance().getExternalContext().
				getRequestParameterMap().get(name);
	}
	
	/**
	 * Redirects to the specified URL.
	 * @param url URL to be redirected.
	 */
	protected void redirect(String url) {
		try {
			getCurrentFacesInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.error("Error while redirecting to: " + url, e);
			showErrorMessage("Error while redirecting to: " + url, "");
		}
	}
	
	/**
	 * @return Response for the current Request.
	 */
	protected HttpServletResponse getResponse() {
		HttpServletResponse response = (HttpServletResponse) 
				FacesContext.getCurrentInstance().getExternalContext().getResponse();
		return response;
	}
	
	/**
	 * @return The current Request.
	 */
	protected HttpServletRequest getRequest() {
		HttpServletRequest request = (HttpServletRequest) 
				FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}
	
	/**
	 * @return Logged User into the App if exists.
	 */
	protected User getLoggedUser() {
		User user = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if(authentication != null){
				Object object = authentication.getPrincipal();
				if(object instanceof User){
					user = (User) object;
				}
			}
		}
		return user;
	}
	
	/**
	 * @return Obtains the current logged Company if exists.
	 * @throws Exception
	 */
	protected Company getLoggedCompany() throws Exception {
		Company company = null;
		User user = getLoggedUser();
		Map<String, Object> parameters = new HashMap<>();
		//Prepare query parameters and send the DB request.
		parameters.put(USER_ID, user.getUserId());
		company = companyService.getCompanyByUserId(parameters);
		return company;
	}
	
	/**
	 * Shows a Generic General Exception Message.
	 */
	protected void showGeneralExceptionMessage() {
		showErrorMessage(internationalizationService
				.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
	}
	
	/**
	 * Shows a Generic MB Startup Exception Message.
	 */
	protected void showStartupMbExceptionMessage() {
		showErrorMessage(internationalizationService
				.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
	}
}
