package japhet.sales.controller.home;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Product;
import japhet.sales.service.IProductService;

@ManagedBean
@SessionScoped
public class HomeMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 999398447133843098L;
	
	private static final String PRODUCT_IMAGE_REQ_PARAM = "productId";

	@Inject
	private Logger logger;
	
	@EJB
	private IProductService productService;
	
	//View properties
	private List<Product> availableProducts;
	private Map<Long, byte[]> imagesMap;
	
	@PostConstruct
	private void init(){
		try {
			logger.info("Initializing HomeMB...");
			//Initialize images map and products
			imagesMap = new HashMap<>();
			setAvailableProducts(productService.getAllAvailableProducts());
			for(Product product : availableProducts){
				imagesMap.put(product.getProductId(), product.getImage());
			}
		} catch (Exception e) {
			logger.error("Error at initializing the HomeMB.", e);
		}
	}

	public List<Product> getAvailableProducts() {
		return availableProducts;
	}

	public void setAvailableProducts(List<Product> availableProducts) {
		this.availableProducts = availableProducts;
	}
	
	public String getPRODUCT_IMAGE_REQ_PARAM(){
		return PRODUCT_IMAGE_REQ_PARAM;
	}
	
	public StreamedContent getStreamedImage() {
		StreamedContent streamedContent = new DefaultStreamedContent();
		try {
			if (getCurrentFacesInstance().getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
				Long productId = Long.valueOf(getRequestParam(PRODUCT_IMAGE_REQ_PARAM));
				streamedContent = new DefaultStreamedContent(
						new ByteArrayInputStream(imagesMap.get(productId)), "image/jpg");
			}
		} catch (Exception e) {
			logger.error("Error while generating image from bytes array.", e);
		}
		return streamedContent;
	}
}
