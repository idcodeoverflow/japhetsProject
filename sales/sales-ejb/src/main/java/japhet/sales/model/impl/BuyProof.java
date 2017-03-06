package japhet.sales.model.impl;

import static japhet.sales.data.QueryNames.*;
import static japhet.sales.data.QueryParameters.*;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.catalogs.Statuses;
import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_BUY_PROOF")
@NamedQueries(value = {
		@NamedQuery(name = GET_BUY_PROOFS_BY_USER, 
				query = "SELECT b FROM BuyProof b WHERE b.user.userId = :" + USER_ID),
		@NamedQuery(name = GET_BUY_PROOFS_BY_USER_N_STATUS, 
				query = "SELECT b FROM BuyProof b WHERE b.user.userId = :" + USER_ID + " AND b.status.statusId = :" + STATUS_ID),
		@NamedQuery(name = UPDATE_BUY_PROOFS_BATCH, 
				query = "UPDATE BuyProof b SET b.paymentRequestId = :" + PAYMENT_REQUEST_ID + ", b.lastUpdate = :" + LAST_UPDATE + ", b.status.statusId = :" + STATUS_ID + " WHERE b IN :" + BUY_PROOFS_TO_UPDATE)
})
public class BuyProof implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 591478177899285187L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUY_PROOF_ID")
	private Long buyProofId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Column(name = "USER_PRODUCT_HISTORIAL_KEY",
			nullable = false)
	private String userProductHistorialKey;
	
	@Lob
	@Column(name = "TICKET_IMAGE",
			nullable = false)
	private byte[] ticketImage;
	
	@Column(name = "PAYBACK_APPLIED")
	private Boolean paybackApplied;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "REGISTERED_ON",
			nullable = false)
	private Date registeredOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VALIDATED_BY")
	private User validatedBy;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "PAYED_ON")
	private Date payedOn;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE",
			nullable = false)
	private Date lastUpdate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID",
			nullable = false)
	private Status status;
	
	@Column(name = "PAYMENT_REQUEST_ID")
	private Long paymentRequestId;
	
	@Column(name = "FILE_NAME",
			nullable = false)
	private String fileName;
	
	@Column(name = "CONTENT_TYPE",
			nullable = false)
	private String contentType;
	
	@Column(name = "TOTAL",
			nullable = false)
	private Double total;
	
	@ManyToOne(fetch = FetchType.EAGER, 
			targetEntity = UserProductHistorial.class)
	@JoinColumn(name = "USER_PRODUCT_HISTORIAL_KEY", 
			referencedColumnName = "USER_PRODUCT_HISTORIAL_KEY",
			insertable = false, 
			updatable = false)
	private UserProductHistorial userProductHistorial;
	
	public BuyProof() {
		this.registeredOn = new Date();
		this.lastUpdate = new Date();
		this.paybackApplied = false;
		//Set initial status
		this.status = new Status();
		this.status.setStatusId(Statuses.VALIDATION_PENDING.getId());
	}

	public BuyProof(Long buyProofId, User user,
			String userProductHistorialKey, byte[] ticketImage,
			Boolean paybackApplied, Date registeredOn, Date payedOn, 
			Date lastUpdate, Status status, Long paymentRequestId,
			User validatedBy, String fileName, String contentType,
			Double total, UserProductHistorial userProductHistorial) {
		super();
		this.buyProofId = buyProofId;
		this.user = user;
		this.userProductHistorialKey = userProductHistorialKey;
		this.ticketImage = ticketImage;
		this.paybackApplied = paybackApplied;
		this.registeredOn = registeredOn;
		this.payedOn = payedOn;
		this.validatedBy = validatedBy;
		this.lastUpdate = lastUpdate;
		this.status = status;
		this.paymentRequestId = paymentRequestId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.total = total;
		this.userProductHistorial = userProductHistorial;
	}

	public Long getBuyProofId() {
		return buyProofId;
	}

	public void setBuyProofId(Long buyProofId) {
		this.buyProofId = buyProofId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserProductHistorialKey() {
		return userProductHistorialKey;
	}

	public void setUserProductHistorialKey(String userProductHistorialKey) {
		this.userProductHistorialKey = userProductHistorialKey;
	}

	public byte[] getTicketImage() {
		return ticketImage;
	}

	public void setTicketImage(byte[] ticketImage) {
		this.ticketImage = ticketImage;
	}

	public Boolean getPaybackApplied() {
		return paybackApplied;
	}

	public void setPaybackApplied(Boolean paybackApplied) {
		this.paybackApplied = paybackApplied;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public User getValidatedBy() {
		return validatedBy;
	}

	public void setValidatedBy(User validatedBy) {
		this.validatedBy = validatedBy;
	}

	public Date getPayedOn() {
		return payedOn;
	}

	public void setPayedOn(Date payedOn) {
		this.payedOn = payedOn;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getPaymentRequestId() {
		return paymentRequestId;
	}

	public void setPaymentRequestId(Long paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public UserProductHistorial getUserProductHistorial() {
		return userProductHistorial;
	}

	public void setUserProductHistorial(UserProductHistorial userProductHistorial) {
		this.userProductHistorial = userProductHistorial;
	}
}
