package japhet.sales.service;

import java.io.Serializable;

import javax.ejb.Local;

import japhet.sales.except.BusinessServiceException;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@Local
public interface IRandomPasswordService extends Serializable {

	/**
	 * Generates a random password of a certain length.
	 * @param length Length desired for the password
	 * @return A random generated Password
	 * @throws BusinessServiceException
	 */
	public String generateRandomPassword(short length) 
			throws BusinessServiceException;
	
}
