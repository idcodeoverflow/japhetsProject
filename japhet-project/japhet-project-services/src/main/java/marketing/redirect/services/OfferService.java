/**
 * 
 */
package marketing.redirect.services;

import java.io.Serializable;
import java.util.List;

/**
 * @author David Garcia
 *
 */
//@Local
public interface OfferService extends Serializable {

	public static final String imagesHome = "resources/media/offers/";
	
	public List<String> getImagesUrl();
	
}
