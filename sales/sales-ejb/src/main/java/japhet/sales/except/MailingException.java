/**
 * 
 */
package japhet.sales.except;

/**
 * @author David Israel Garcia Alcazar
 *
 */
public class MailingException extends Exception {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 9097987750293186094L;

	public MailingException() { 
		super(); 
	}
	
	public MailingException(String message) { 
		super(message); 
	}
	
	public MailingException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	public MailingException(Throwable cause) { 
		super(cause);
	}
}
