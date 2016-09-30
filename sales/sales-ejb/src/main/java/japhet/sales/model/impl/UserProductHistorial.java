package japhet.sales.model.impl;

import java.util.Date;

import static japhet.sales.data.QueryNames.*;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.model.IEntity;

@Entity
@Cacheable(value = true)
@Table(name = "TB_USR_PRDCT_HISTORIAL")
@NamedQueries(value = {
		@NamedQuery(name = GET_ALL_PRODUCT_HISTORIAL_BY_USER, 
				query = "SELECT u FROM UserProductHistorial u WHERE u.user.userId = :userId"),
		@NamedQuery(name = GET_ALL_PRODUCT_HISTORIAL_BY_PRODUCT, 
				query = "SELECT u FROM UserProductHistorial u WHERE u.product.productId = :productId")
})
public class UserProductHistorial implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5094340844910736900L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HISTORIAL_ID")
	private Long historialId;
	
	@JoinColumn(name = "PRODUCT_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@JoinColumn(name = "USER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CLICK_DATE")
	private Date clickDate;
	
	@Column(name = "COMPLETED", 
			nullable = true)
	private Boolean completed;
	
	public UserProductHistorial() {}

	public UserProductHistorial(Long historialId, Product product, 
			User user, Date clickDate, Boolean completed) {
		super();
		this.historialId = historialId;
		this.product = product;
		this.user = user;
		this.clickDate = clickDate;
		this.completed = completed;
	}
	

	public Long getHistorialId() {
		return historialId;
	}

	public void setHistorialId(Long historialId) {
		this.historialId = historialId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getClickDate() {
		return clickDate;
	}

	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
}
