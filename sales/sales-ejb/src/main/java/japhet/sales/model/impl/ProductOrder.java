package japhet.sales.model.impl;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.catalogs.Statuses;
import japhet.sales.model.IEntity;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@Cacheable(value = true)
@Entity
@Table(name = "TB_PRODUCT_ORDER")
public class ProductOrder implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5801347955897147638L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ORDER_ID")
	private Long productOrderId;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", 
			nullable = false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = User.class)
	@JoinColumn(name = "USER_ID",
			nullable = false)
	private User user;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "TOTAL")
	private Double total;
	
	@Column(name = "PAYBACK_AMOUNT")
	private Double paybackAmount;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = Status.class)
	@JoinColumn(name = "STATUS_ID",
			nullable = false)
	private Status status;
	
	@Column(name = "CREATED_ON", 
			nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name = "LAST_UPDATE", 
			nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column(name = "RESOLUTION_DATE", 
			nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date resolutionDate;
	
	/**
	 * Default constructor.
	 */
	public ProductOrder() {
		this.status = new Status();
		status.setStatusId(Statuses.VALIDATION_PENDING.getId());
		this.product = new Product();
		this.createdOn = new Date();
		this.lastUpdate = new Date();
	}

	/**
	 * Constructor with all fields.
	 * @param productOrderId
	 * @param product
	 * @param user
	 * @param price
	 * @param quantity
	 * @param total
	 * @param paybackAmount
	 * @param status
	 * @param createdOn
	 * @param lastUpdate
	 * @param resolutionDate
	 */
	public ProductOrder(Long productOrderId, Product product, User user, 
			Double price, Integer quantity, Double total,
			Double paybackAmount, Status status, Date createdOn, 
			Date lastUpdate, Date resolutionDate) {
		super();
		this.productOrderId = productOrderId;
		this.product = product;
		this.user = user;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
		this.paybackAmount = paybackAmount;
		this.status = status;
		this.createdOn = createdOn;
		this.lastUpdate = lastUpdate;
		this.resolutionDate = resolutionDate;
	}

	/**
	 * @return the productOrderId
	 */
	public Long getProductOrderId() {
		return productOrderId;
	}

	/**
	 * @param productOrderId the productOrderId to set
	 */
	public void setProductOrderId(Long productOrderId) {
		this.productOrderId = productOrderId;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the paybackAmount
	 */
	public Double getPaybackAmount() {
		return paybackAmount;
	}

	/**
	 * @param paybackAmount the paybackAmount to set
	 */
	public void setPaybackAmount(Double paybackAmount) {
		this.paybackAmount = paybackAmount;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the resolutionDate
	 */
	public Date getResolutionDate() {
		return resolutionDate;
	}

	/**
	 * @param resolutionDate the resolutionDate to set
	 */
	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
}
