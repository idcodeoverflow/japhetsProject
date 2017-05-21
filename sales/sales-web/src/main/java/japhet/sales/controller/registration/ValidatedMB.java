package japhet.sales.controller.registration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import japhet.sales.controller.GenericMB;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@ManagedBean
@NoneScoped
public class ValidatedMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2746455224811580396L;

	/**
	 * This method redirects to the Sign In web page.
	 */
	public void signInRedirect() {
		try {
			final String INFO_MSG = "Redirecting to the sign in page...";
			logger.info(INFO_MSG);
			redirect(SIGN_IN_URL);
		} catch (Exception e) {
			final String ERROR_MSG = "An exception has occurred while redirecting to the sign in url.";
			logger.error(ERROR_MSG, e);
		}
	}
}
