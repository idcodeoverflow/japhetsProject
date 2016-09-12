/**
 * 
 */
package japhet.sales.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import japhet.sales.controller.GenericMB;
import japhet.sales.service.OfferService;
import japhet.sales.service.impl.OfferServiceImp;

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
