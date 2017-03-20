/**
 * 
 */
package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.UserInformation;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@Stateless
public class UserInformationDAO extends GenericDAO<UserInformation, Long> {

	/**
	 * Default constructor.
	 */
	public UserInformationDAO() {
		super(UserInformation.class, Long.class);
	}	
}
