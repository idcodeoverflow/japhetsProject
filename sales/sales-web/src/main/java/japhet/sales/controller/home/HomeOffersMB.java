/**
 * 
 */
package japhet.sales.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import japhet.sales.controller.GenericMB;
import japhet.sales.service.IOfferService;
import japhet.sales.service.impl.OfferService;

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
	@EJB
	private IOfferService offerServiceImp;
	
	private List<String> imagesPath;
	private final int scrollerChunkSize = 10;
	
	@PostConstruct
	public void init(){
		//offerServiceImp = new OfferService();
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

	public void setOfferServiceImp(OfferService offerServiceImp) {
		this.offerServiceImp = offerServiceImp;
	}
}
