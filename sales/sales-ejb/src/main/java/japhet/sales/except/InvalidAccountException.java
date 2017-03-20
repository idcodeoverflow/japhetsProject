package japhet.sales.except;

/**
 * @author David Israel Garcia Alcazar
 *
 */
public class InvalidAccountException extends Exception {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -1078326572634148981L;

	public InvalidAccountException() { 
		super(); 
	}
	
	public InvalidAccountException(String message) { 
		super(message); 
	}
	
	public InvalidAccountException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	public InvalidAccountException(Throwable cause) { 
		super(cause);
	}
}
