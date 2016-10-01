package japhet.sales.model.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.data.QueryNames;
import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_PRODUCT")
@NamedQueries({
		@NamedQuery(name = QueryNames.GET_AVAILABLE_PRODUCTS,
				query = "SELECT p FROM Product p WHERE p.startDate <= CURRENT_DATE AND p.endDate >= CURRENT_DATE")
})
public class Product implements IEntity {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -2885883717993765366L;
	
	@Inject
	private transient Logger logger;

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
	
	@Lob
	@Column(name = "IMAGE")
	private byte[] image;
	
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
			Double price, byte[] image, Date uploadDate, Date startDate, 
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

	public byte[] getImage() {
		return image;
	}
	
	public Image getImageFile() throws IOException {
		return convertByteArrayToFile(image);
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public void setImage(File file) throws IOException {
		this.image = convertFileTobytesArray(file);
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
	
	@SuppressWarnings("resource")
	private byte[] convertFileTobytesArray(File file) throws IOException {
		logger.info("Converting file to byte array...");
		InputStream is = new FileInputStream(file);
		long length = file.length();
		
		if (length > Integer.MAX_VALUE) {
			throw new IOException("The file is too big for the DB.");
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = is.read(bytes, offset, bytes.length - offset);
		while (offset < bytes.length && numRead != 0) {
			offset += numRead;
			numRead = is.read(bytes, offset, bytes.length - offset);
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		is.close();
		logger.info("File succesfully converted to bytes array.");
		
		return bytes;
	}
	
	private Image convertByteArrayToFile(byte[] bytes) throws IOException {
		logger.info("Converting byte array to image...");
		Image image = null;
		BufferedImage bfImage = null;
		bfImage = ImageIO.read(new ByteArrayInputStream(bytes));
		image = bfImage;
		logger.info("Bytes array succesfully converted to image.");
		return image;
	}
}
