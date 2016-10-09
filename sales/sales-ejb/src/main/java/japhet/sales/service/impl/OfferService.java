/**
 * 
 */
package japhet.sales.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import japhet.sales.service.IOfferService;

/**
 * @author David
 *
 */
@LocalBean
@Stateless
public class OfferService implements IOfferService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 2723502707221371914L;
	
	private final int imageLoadBoundary = 1000;

	/* (non-Javadoc)
	 * @see marketing.redirect.services.OfferService#getImagesUrl()
	 */
	@Override
	public List<String> getImagesUrl() {
		List<String> images = new ArrayList<>();
		String imagePath = imagesHome + "a";
		String tempImage = "";
		for(int imageIndex = 0; imageIndex <= imageLoadBoundary; imageIndex++){
			tempImage = String.format("%s%d.jpg", imagePath, imageIndex % 12);
			images.add(tempImage);
		}
		return images;
	}

}
