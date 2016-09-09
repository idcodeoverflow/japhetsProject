package marketing.redirect.entities.impl;

import java.sql.Blob;
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

import marketing.redirect.entities.IEntity;

@Entity
@Cacheable(value = true)
@Table(name = "TB_PRODUCT")
public class Product implements IEntity {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -2885883717993765366L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "IMAGE")
	private Blob image;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_DATE")
	private Date uploadDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@Column(name = "REDIRECT_NUMBER")
	private Integer redirectNumber;
	
	public Product() {}

	public Product(Long productId, String title, String description, 
			Double price, Blob image, Date uploadDate, Date startDate, 
			Date endDate, Company company, Category category, 
			Integer redirectNumber) {
		super();
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.price = price;
		this.image = image;
		this.uploadDate = uploadDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.company = company;
		this.category = category;
		this.redirectNumber = redirectNumber;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getRedirectNumber() {
		return redirectNumber;
	}

	public void setRedirectNumber(Integer redirectNumber) {
		this.redirectNumber = redirectNumber;
	}
}
