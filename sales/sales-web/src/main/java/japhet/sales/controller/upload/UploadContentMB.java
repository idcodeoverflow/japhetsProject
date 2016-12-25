package japhet.sales.controller.upload;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import japhet.sales.controller.GenericMB;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidDateRangeException;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.model.impl.Category;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.Product;
import japhet.sales.service.IProductService;
import japhet.sales.service.IUtilService;

@ManagedBean
@ViewScoped
public class UploadContentMB extends GenericMB {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 7982763050146800232L;

	@Inject
	private Logger logger;

	//EJB's
	@EJB
	private IProductService productService;
	
	@EJB
	private IUtilService utilService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	//Input properties
	private byte []imageBytes;
	private Category category;
	private Company company;
	
	
	//Object to persist
	private Product product;
	
	//Validation properties
	private final int MAX_MEDIA_SIZE = 2500000;
	
	@PostConstruct
	public void init(){
		try {
			logger.info("Initializing UploadContentMB...");
			clearAll();
		} catch (Exception e) {
			logger.error("An error has ocurred while initializing UploadContentMB.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
			
	public void handleFileUpload(FileUploadEvent event) {
        try {
			imageBytes = utilService.getBiteArrayFromStream(
					event.getFile().getInputstream());
			showInfoMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getIMAGE_READY()), "");
		} catch (Exception e) {
			logger.error("There was an error uploading the image to the server.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getIMAGE_UPLOAD_ERROR()), 
					event.getFile().getFileName());
		}
    }
	
	public void saveProduct() {
		//TODO: complete the login to obtain the CompanyId
		company.setCompanyId(1L);
		//Set remaining values
		product.setCategory(category);
		product.setCompany(company);
		product.setImage(imageBytes);
		try {
			//Divide the entry to generate the percentage
			product.setPaybackPercent((double)(product.getPaybackPercent() / 100.0));
			//Persist product
			if(productService.insertProduct(product)){
				logger.info("Content succesfully saved.");
				showInfoMessage(internationalizationService
						.getI18NMessage(CURRENT_LOCALE, getCONTENT_READY()), "");
				clearAll();
			} else {
				logger.error("An error has ocurred (product insert).");
				showErrorMessage(internationalizationService
						.getI18NMessage(CURRENT_LOCALE, getSAVE_CONTENT_ERROR()), "");
			}
		} catch (InvalidDateRangeException e) {
			logger.error("Invalid range for the dates.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getDATE_RANGE_INVALID()), "");
		} catch (BusinessServiceException e) {
			logger.error("An error has occurred while saving the content.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSAVE_CONTENT_ERROR()), "");
		}
	}
	
	public void clearAll(){
		product = new Product();
		company = new Company();
		category = new Category();
		imageBytes = new byte[MAX_MEDIA_SIZE];
		//Initialize the number of redirections to this product in 0
		product.setRedirectNumber(0);
	}
	
	public long getMAX_MEDIA_SIZE() {
		return MAX_MEDIA_SIZE;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Date getCurrentDate() {
		return new Date();
	}
}
