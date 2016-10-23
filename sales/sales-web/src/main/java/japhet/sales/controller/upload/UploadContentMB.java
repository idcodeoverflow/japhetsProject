package japhet.sales.controller.upload;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import japhet.sales.controller.GenericMB;
import japhet.sales.except.BusinessServiceException;
import japhet.sales.except.InvalidDateRangeException;
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
	
	//EJB's
	@EJB
	private IProductService productService;
	@EJB
	private IUtilService utilService;
	
	//Input properties
	private byte []imageBytes;
	private Category category;
	private Company company;
	
	
	//Object to persist
	private Product product;
	
	//Validation properties
	private final long MAX_MEDIA_SIZE = 150000;
	
	@PostConstruct
	public void init(){
		clearAll();
	}
			
	public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Imagen cargada: ", 
        		event.getFile().getFileName());
        getCurrentFacesInstance().addMessage(null, message);
        try {
			imageBytes = utilService.getBiteArrayFromStream(
					event.getFile().getInputstream());
			showInfoMessage("La imagen está lista para guardarse,", "");
		} catch (Exception e) {
			showErrorMessage("Ocurrió un error al subir la imagen.", 
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
				showInfoMessage("Contenido guardado", "");
				logger.info("Content succesfully saved.");
				clearAll();
			} else {
				showErrorMessage("El producto falló al guardarse,\nintentalo mas tarde", "");
				logger.error("An error has ocurred (product insert).");
			}
		} catch (InvalidDateRangeException e) {
			showErrorMessage("El rango de fechas es invalido", "");
			logger.error("Invalid range for the dates.", e);
		} catch (BusinessServiceException e) {
			showErrorMessage("Ocurrio un error al guardar el contenido.", "");
			logger.error("An error has occurred while saving the content.", e);
		}
	}
	
	public void clearAll(){
		product = new Product();
		company = new Company();
		category = new Category();
		imageBytes = new byte[151000];
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
