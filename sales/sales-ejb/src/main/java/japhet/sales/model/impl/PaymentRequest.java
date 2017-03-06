package japhet.sales.model.impl;

import static japhet.sales.data.QueryNames.GET_PAYMENT_REQUEST_BY_USER;
import static japhet.sales.data.QueryNames.GET_PAYMENT_REQUEST_BY_STATUS;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.catalogs.Statuses;
import japhet.sales.model.IEntity;
import japhet.sales.model.ISequenceTable;

@Cacheable(value = true)
@Entity
@Table(name = "TB_PAYMENT_REQUEST")
@NamedQueries(value = {
		@NamedQuery(name = GET_PAYMENT_REQUEST_BY_USER, 
			query = "SELECT p FROM PaymentRequest p WHERE p.user.userId = :userId"),
		@NamedQuery(name = GET_PAYMENT_REQUEST_BY_STATUS, 
			query = "SELECT p FROM PaymentRequest p WHERE p.status.statusId = :statusId")
})
public class PaymentRequest implements IEntity, ISequenceTable {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 1680326723326533532L;
	
	private static long paymentRequestSequence = 1L;

	@Id
	@Column(name = "PAYMENT_REQUEST_ID")
	private Long paymentRequestId;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "REQUEST_DATE")
	private Date requestDate;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VALIDATED_BY", 
			nullable = true)
	private User validatedBy;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "RESOLUTION_DATE", 
			nullable = true)
	private Date resolutionDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;				
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENT_REQUEST_ID", 
			updatable = false, 
			insertable = false)
	private List<BuyProof> buyProofs;
	
	public PaymentRequest() {
		super();
		this.requestDate = new Date();
		this.lastUpdate = new Date();
		//Set initial
		this.status = new Status();
		this.status.setStatusId(Statuses.VALIDATION_PENDING.getId());
	}

	public PaymentRequest(Long paymentRequestId, Double amount, 
			Date requestDate, Date lastUpdate, Date resolutionDate,
			Status status, User user, List<BuyProof> buyProofs,
			User validatedBy) {
		super();
		this.paymentRequestId = paymentRequestId;
		this.amount = amount;
		this.requestDate = requestDate;
		this.lastUpdate = lastUpdate;
		this.validatedBy = validatedBy;
		this.resolutionDate = resolutionDate;
		this.status = status;
		this.user = user;
		this.buyProofs = buyProofs;
	}

	public Long getPaymentRequestId() {
		return paymentRequestId;
	}

	public void setPaymentRequestId(Long paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public User getValidatedBy() {
		return validatedBy;
	}

	public void setValidatedBy(User validatedBy) {
		this.validatedBy = validatedBy;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<BuyProof> getBuyProofs() {
		return buyProofs;
	}

	public void setBuyProofs(List<BuyProof> buyProofs) {
		this.buyProofs = buyProofs;
	}
	
	//Sequence methods
	
	/**
	 * Obtains the current value to use as PK in this entity
	 * @return
	 */
	public synchronized long getCurrentSequenceValue() {
		return paymentRequestSequence;
	}
	
	/**
	 * Obtains the current value to use as PK in this entity
	 * and then increases its value by 1.
	 * @return
	 */
	public synchronized long getNewSequenceValue() {
		return paymentRequestSequence++;
	}
	
	/**
	 * Increases by 1 the current PK sequence value.
	 */
	public synchronized void increaseSequenceValue() {
		++paymentRequestSequence;
	}
	
	/**
	 * Defines the value that is going to be used in 
	 * the sequence generator.
	 */
	public synchronized void setSequenceValue(long sequenceValue) {
		paymentRequestSequence = sequenceValue;
	}
}
