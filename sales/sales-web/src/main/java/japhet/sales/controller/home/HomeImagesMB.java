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
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.Product;
import japhet.sales.service.ICompanyService;
import japhet.sales.service.IProductService;

@ManagedBean
@ApplicationScoped
public class HomeImagesMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1192715924260847873L;
	private static final String PRODUCT_IMAGE_REQ_PARAM = "productId";
	private static final String COMPANY_IMAGE_REQ_PARAM = "companyId";

	//View properties
	private Map<Long, byte[]> imagesMap;
	private Map<Long, byte[]> companyImagesMap;
	
	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private IProductService productService;
	
	@EJB
	private ICompanyService companyService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	@PostConstruct
	public void init() {
		logger.info("Initializing HomeImagesMB...");
		imagesMap = new HashMap<>();
		companyImagesMap = new HashMap<>();
		try {
			//Get product images
			List<Product> products = productService.getAllAvailableProducts();
			for(Product prod : products) {
				imagesMap.put(prod.getProductId(), prod.getImage());
			}
			//Get company images
			List<Company> companies = companyService.getAllAvailableCompanies();
			for(Company company : companies) {
				companyImagesMap.put(company.getCompanyId(), company.getImage());
			}
		} catch (Exception e) {
			logger.fatal("Error initializing the HomeImagesMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}

	public StreamedContent getStreamedImage() {
		return generateStreamedContent(imagesMap, PRODUCT_IMAGE_REQ_PARAM);
	}
	
	public StreamedContent getStreamedCompanyImage() {
		return generateStreamedContent(companyImagesMap, COMPANY_IMAGE_REQ_PARAM);
	}
	
	private StreamedContent generateStreamedContent(Map<Long, byte[]> imagesMap, 
			final String REQ_PARAM) {
		StreamedContent streamedContent = new DefaultStreamedContent();
		try {
			if (getCurrentFacesInstance().getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
				Long companyId = Long.valueOf(getRequestParam(REQ_PARAM));
				streamedContent = new DefaultStreamedContent(
						new ByteArrayInputStream(imagesMap.get(companyId)), "image/jpg");
			}
		} catch (Exception e) {
			logger.error("Error while generating the company image from bytes array.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getGENERAL_ERROR()), "");
		}
		return streamedContent;
	}
	
	public Map<Long, byte[]> getImagesMap() {
		return imagesMap;
	}

	public void setImagesMap(Map<Long, byte[]> imagesMap) {
		this.imagesMap = imagesMap;
	}
	
	public Map<Long, byte[]> getCompanyImagesMap() {
		return companyImagesMap;
	}

	public void setCompanyImagesMap(Map<Long, byte[]> companyImagesMap) {
		this.companyImagesMap = companyImagesMap;
	}

	public String getPRODUCT_IMAGE_REQ_PARAM() {
		return PRODUCT_IMAGE_REQ_PARAM;
	}	
	
	public String getCOMPANY_IMAGE_REQ_PARAM() {
		return COMPANY_IMAGE_REQ_PARAM;
	}
}
