package japhet.sales.model.impl;

import static japhet.sales.data.QueryNames.*;

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
				query = "SELECT b FROM BuyProof b WHERE b.user.userId = :userId"),
		@NamedQuery(name = GET_BUY_PROOFS_BY_PRODUCT, 
				query = "SELECT b FROM BuyProof b WHERE b.product.productId = :productId")
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	@Column(name = "FINGER_PRINT")
	private String fingerPrint;
	
	@Lob
	@Column(name = "TICKET_IMAGE")
	private byte[] ticketImage;
	
	@Column(name = "PAYBACK_APPLIED")
	private Boolean paybackApplied;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "REGISTERED_ON")
	private Date registeredOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VALIDATED_BY")
	private User validatedBy;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "PAYED_ON")
	private Date payedOn;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID")
	private Status status;
	
	@Column(name = "PAYMENT_REQUEST_ID")
	private Long paymentRequestId;
	
	public BuyProof() {
		this.registeredOn = new Date();
		this.lastUpdate = new Date();
		this.paybackApplied = false;
		//Set initial status
		this.status = new Status();
		this.status.setStatusId(Statuses.VALIDATION_PENDING.getId());
	}

	public BuyProof(Long buyProofId, User user, Product product, 
			String fingerPrint, byte[] ticketImage,
			Boolean paybackApplied, Date registeredOn, Date payedOn, 
			Date lastUpdate, Status status, Long paymentRequestId,
			User validatedBy) {
		super();
		this.buyProofId = buyProofId;
		this.user = user;
		this.product = product;
		this.fingerPrint = fingerPrint;
		this.ticketImage = ticketImage;
		this.paybackApplied = paybackApplied;
		this.registeredOn = registeredOn;
		this.payedOn = payedOn;
		this.validatedBy = validatedBy;
		this.lastUpdate = lastUpdate;
		this.status = status;
		this.paymentRequestId = paymentRequestId;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
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
}
