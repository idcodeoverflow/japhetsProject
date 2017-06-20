package japhet.sales.rest.model.impl;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
public class GuestSession {

	private String sessionKey;
	
	private Boolean validSession;
	
	public GuestSession() {
		this.sessionKey = null;
		this.validSession = false;
	}

	public GuestSession(String sessionKey, Short htmlReponseCode, Boolean validSession) {
		super();
		this.sessionKey = sessionKey;
		this.validSession = validSession;
	}

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * @param sessionKey the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the validSession
	 */
	public Boolean getValidSession() {
		return validSession;
	}

	/**
	 * @param validSession the validSession to set
	 */
	public void setValidSession(Boolean validSession) {
		this.validSession = validSession;
	}
}
