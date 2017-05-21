package japhet.sales.except;

public class InvalidPasswordException extends Exception {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 8058120245788863480L;
	
	public InvalidPasswordException() { 
		super(); 
	}
	
	public InvalidPasswordException(String message) { 
		super(message); 
	}
	
	public InvalidPasswordException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	public InvalidPasswordException(Throwable cause) { 
		super(cause);
	}
}
