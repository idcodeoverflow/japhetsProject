package japhet.sales.model.impl;

import java.util.Date;
import java.util.List;

import static japhet.sales.data.QueryNames.*;
import static japhet.sales.data.QueryParameters.*;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.model.IEntity;
import japhet.sales.util.Encryption;

@Cacheable(value = true)
@Entity
@Table(name = "TB_USR_PRDCT_HISTORIAL")
@NamedQueries(value = {
		@NamedQuery(name = GET_ALL_PRODUCT_HISTORIAL_BY_USER, 
				query = "SELECT u FROM UserProductHistorial u WHERE u.user.userId = :" + USER_ID),
		@NamedQuery(name = GET_COMPLETED_PRDCT_HIST_BY_USER, 
				query = "SELECT u FROM UserProductHistorial u WHERE u.user.userId = :" + USER_ID + " AND u.completed = TRUE AND u.buyProofs IS EMPTY"),
		@NamedQuery(name = GET_ALL_PRODUCT_HISTORIAL_BY_PRODUCT, 
				query = "SELECT u FROM UserProductHistorial u WHERE u.product.productId = :" + PRODUCT_ID),
		@NamedQuery(name = GET_ALL_PRODUCT_HISTORIAL_BY_FPRINT, 
				query = "SELECT u FROM UserProductHistorial u WHERE u.userProductHistorialKey = :" + FINGERPRINT)
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
	
	@JoinColumn(name = "USER_ID", 
			nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLICK_DATE")
	private Date clickDate;
	
	@Column(name = "COMPLETED", 
			nullable = true)
	private Boolean completed;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETED_DATE",
			nullable = true)
	private Date completedDate;
	
	@Column(name = "USER_PRODUCT_HISTORIAL_KEY", 
			nullable = true)
	private String userProductHistorialKey;
	
	@Column(name = "TOTAL",
			nullable = true)
	private Double total;
	
	@Column(name = "PRODUCTS_COUNT",
			nullable = true)
	private Integer productsCount;
	
	@Column(name = "RECEIPT", 
			nullable = true)
	private String receipt;
	
	@JoinColumn(name = "USER_PRODUCT_HISTORIAL_KEY",
			referencedColumnName = "USER_PRODUCT_HISTORIAL_KEY",
			nullable = true)
	@OneToMany(fetch = FetchType.EAGER)
	private List<BuyProof> buyProofs;
	
	public UserProductHistorial() {}

	public UserProductHistorial(Long historialId, Product product, 
			User user, Date clickDate, Boolean completed,
			Date completedDate, String userProductHistorialKey,
			Double total, Integer productsCount, String receipt,
			List<BuyProof> buyProofs) {
		super();
		this.historialId = historialId;
		this.product = product;
		this.user = user;
		this.clickDate = clickDate;
		this.completed = completed;
		this.completedDate = completedDate;
		this.userProductHistorialKey = userProductHistorialKey;
		this.total = total;
		this.productsCount = productsCount;
		this.receipt = receipt;
		this.buyProofs = buyProofs;
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
	
	public Date getCompletedDate() {
		return completedDate;
	}
	
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	public String getUserProductHistorialKey() {
		return userProductHistorialKey;
	}
	
	public void setUserProductHistorialKey(String userProductHistorialKey) {
		this.userProductHistorialKey = userProductHistorialKey;
	}
	
	public void generateUserProductHistorialKey() throws Exception {
		final String str = String.format("UserId:%dProductId:%dTimestamp:%s", 
				this.user.getUserId(), this.product.getProductId(), this.clickDate.toString());
		this.userProductHistorialKey = Encryption.SHA256(str);
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(Integer productsCount) {
		this.productsCount = productsCount;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public List<BuyProof> getBuyProofs() {
		return buyProofs;
	}

	public void setBuyProofs(List<BuyProof> buyProofs) {
		this.buyProofs = buyProofs;
	}
}
