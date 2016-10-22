package japhet.sales.except;

public class BusinessServiceException extends Exception {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 4685881344255404485L;

	public BusinessServiceException() { 
		super(); 
	}
	
	public BusinessServiceException(String message) { 
		super(message); 
	}
	
	public BusinessServiceException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	public BusinessServiceException(Throwable cause) { 
		super(cause);
	}

}
