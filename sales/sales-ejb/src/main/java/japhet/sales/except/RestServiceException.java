package japhet.sales.except;

/**
 * @author David Israel Garcia Alcazar
 *
 */
public class RestServiceException extends Exception {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 7511928703520698418L;

	public RestServiceException() { 
		super(); 
	}
	
	public RestServiceException(String message) { 
		super(message); 
	}
	
	public RestServiceException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	public RestServiceException(Throwable cause) { 
		super(cause);
	}
}
