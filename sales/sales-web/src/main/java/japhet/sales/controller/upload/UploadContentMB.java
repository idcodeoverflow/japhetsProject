package japhet.sales.controller.upload;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Category;
import japhet.sales.model.impl.Company;
import japhet.sales.model.impl.Product;
import japhet.sales.service.ICategoryService;
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
		imageBytes = new byte[151000];
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
		//Set remaining values
		product.setCategory(category);
		product.setCompany(company);
		product.setImage(imageBytes);
		//Persist product
		if(productService.insertProduct(product)){
			showInfoMessage("Guardo satisfactoriamente", "");
		} else {
			showErrorMessage("El producto falló al guardarse,\nintentalo mas tarde", "");
		}
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

}
