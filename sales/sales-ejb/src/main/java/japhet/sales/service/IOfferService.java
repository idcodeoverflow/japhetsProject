/**
 * 
 */
package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author David Garcia
 *
 */
//@Local
public interface IOfferService extends Serializable {

	public static final String imagesHome = "resources/media/offers/";
	
	public List<String> getImagesUrl();
	
}
