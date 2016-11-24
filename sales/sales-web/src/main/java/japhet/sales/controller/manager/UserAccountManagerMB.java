package japhet.sales.controller.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.model.impl.Category;
import japhet.sales.service.ICategoryService;

@ManagedBean
@ViewScoped
public class UserAccountManagerMB extends GenericMB {

	/**
	 * Maven generated
	 */
	private static final long serialVersionUID = -5288053365897420779L;
	
	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private ICategoryService categoryService;
	
	//View properties
	private List<Category> categories;
	
	//Validation properties
	private final int MAX_MEDIA_SIZE = 30000;
	
	@PostConstruct
	private void init() {
		logger.info("Initializing user account manager...");
		try {
			categories = categoryService.getAllAvailableCategories();
		} catch (Exception e) {
			logger.error("Error while initializing user account manager.", e);
			showErrorMessage("Ocurrio un error al cargar esta página.", "");
		}
	}
	
	public void saveFavoriteCategories() {
		
	}

	public List<String> getDeposits() {
		List<String> deposits = new ArrayList<>();
		deposits.add("182646");
		deposits.add("182878");
		deposits.add("182656");
		deposits.add("182686");
		return deposits;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public int getMAX_MEDIA_SIZE() {
		return MAX_MEDIA_SIZE;
	}
}
