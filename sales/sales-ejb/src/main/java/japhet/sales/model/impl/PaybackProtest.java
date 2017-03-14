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
import javax.persistence.Lob;
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
@Table(name = "TB_PAYBACK_PROTEST")
public class PaybackProtest implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 4803438222250147010L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYBACK_PROTEST_ID",
			nullable = false)
	private Long paybackProtestId;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID",
			nullable = false)
	private Company company;

	@Column(name = "DESCRIPTION",
			nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = BuyProof.class)
	@JoinColumn(name = "BUY_PROOF_ID",
			nullable = false)
	private BuyProof buyProof;

	@Column(name = "CREATED_ON",
			nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "LAST_UPDATE",
			nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = Status.class)
	@JoinColumn(name = "STATUS_ID",
			nullable = false)
	private Status status;

	@Column(name = "FILE_NAME",
			nullable = false)
	private String fileName;

	@Lob
	@Column(name = "CONTENT_TYPE",
			nullable = false)
	private String contentType;

	@Column(name = "FILE_CONTENT",
			nullable = false)
	private byte[] fileContent;

	@Column(name = "RESOLUTION_DATE",
			nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date resolutionDate;
	
	@ManyToOne(fetch = FetchType.EAGER,
			targetEntity = User.class)
	@JoinColumn(name = "VALIDATED_BY",
			nullable = true)
	private User validatedBy;
	
	/**
	 * Default constructor.
	 */
	public PaybackProtest() {
		this.company = new Company();
		this.buyProof = new BuyProof();
		this.createdOn = new Date();
		this.lastUpdate = new Date();
		//Define status
		this.status = new Status();
		this.status.setStatusId(Statuses.VALIDATION_PENDING.getId());
	}

	/**
	 * @param paybackProtestId
	 * @param company
	 * @param description
	 * @param buyProof
	 * @param createdOn
	 * @param lastUpdate
	 * @param status
	 * @param fileName
	 * @param contentType
	 * @param fileContent
	 * @param resolutionDate
	 * @param validatedBy
	 */
	public PaybackProtest(Long paybackProtestId, Company company, String description, BuyProof buyProof, Date createdOn,
			Date lastUpdate, Status status, String fileName, String contentType, byte[] fileContent,
			Date resolutionDate, User validatedBy) {
		super();
		this.paybackProtestId = paybackProtestId;
		this.company = company;
		this.description = description;
		this.buyProof = buyProof;
		this.createdOn = createdOn;
		this.lastUpdate = lastUpdate;
		this.status = status;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileContent = fileContent;
		this.resolutionDate = resolutionDate;
		this.validatedBy = validatedBy;
	}

	/**
	 * @return the paybackProtestId
	 */
	public Long getPaybackProtestId() {
		return paybackProtestId;
	}

	/**
	 * @param paybackProtestId the paybackProtestId to set
	 */
	public void setPaybackProtestId(Long paybackProtestId) {
		this.paybackProtestId = paybackProtestId;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the buyProof
	 */
	public BuyProof getBuyProof() {
		return buyProof;
	}

	/**
	 * @param buyProof the buyProof to set
	 */
	public void setBuyProof(BuyProof buyProof) {
		this.buyProof = buyProof;
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
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

	/**
	 * @return the validatedBy
	 */
	public User getValidatedBy() {
		return validatedBy;
	}

	/**
	 * @param validatedBy the validatedBy to set
	 */
	public void setValidatedBy(User validatedBy) {
		this.validatedBy = validatedBy;
	}
}
