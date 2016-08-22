/**
 * 
 */
package marketing.redirect.web.controllers.home;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.naming.Context;

import marketing.redirect.services.OfferService;
import marketing.redirect.services.impl.OfferServiceImp;
import marketing.redirect.web.controllers.GenericMB;

/**
 * @author David
 *
 */
@ManagedBean
@ViewScoped
public class HomeOffersMB extends GenericMB {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 9004457004000035745L;
	
	//EJBs
	private OfferService offerServiceImp;
	
	private List<String> imagesPath;
	private final int scrollerChunkSize = 10;
	
	@PostConstruct
	public void init(){
		offerServiceImp = new OfferServiceImp();
		imagesPath = offerServiceImp.getImagesUrl();
		for(String pic : imagesPath){
			System.out.println("DEBUGGER: " + pic);
		}
	}
	
	public List<String> getImagesPath(){
		return ((imagesPath == null) ? new ArrayList<String>() : imagesPath);
	}
	
	public int getScrollerChunkSize(){
		return scrollerChunkSize;
	}

	public void setOfferServiceImp(OfferServiceImp offerServiceImp) {
		this.offerServiceImp = offerServiceImp;
	}
}
