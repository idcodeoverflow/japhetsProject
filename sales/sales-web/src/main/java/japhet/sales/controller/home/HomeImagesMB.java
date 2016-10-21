package japhet.sales.controller.home;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
public class HomeImagesMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1192715924260847873L;
	private static final String PRODUCT_IMAGE_REQ_PARAM = "productId";

	//View properties
	private Map<Long, byte[]> imagesMap;
	
	@Inject
	private Logger logger;
	
	@EJB
	private IProductService productService;
	
	@PostConstruct
	public void init(){
		logger.info("Initializing HomeImagesMB...");
		imagesMap = new HashMap<>();
		try {
			List<Product> products = productService.getAllAvailableProducts();
			for(Product prod : products){
				imagesMap.put(prod.getProductId(), prod.getImage());
			}
		} catch (Exception e) {
			logger.fatal("Error initializing the HomeImagesMB.", e);
		}
	}

	public Map<Long, byte[]> getImagesMap() {
		return imagesMap;
	}

	public void setImagesMap(Map<Long, byte[]> imagesMap) {
		this.imagesMap = imagesMap;
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
	
	public String getPRODUCT_IMAGE_REQ_PARAM(){
		return PRODUCT_IMAGE_REQ_PARAM;
	}	
}
