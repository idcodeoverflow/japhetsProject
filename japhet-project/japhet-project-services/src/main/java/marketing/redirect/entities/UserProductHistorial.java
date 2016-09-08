package marketing.redirect.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_USR_PRDCT_HISTORIAL")
public class UserProductHistorial implements Serializable {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 5094340844910736900L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HISTORIAL_ID")
	private Long historialId;
	
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CLICK_DATE")
	private Date clickDate;
	
	@Column(name = "COMPLETED", 
			nullable = true)
	private Boolean completed;
	
	public UserProductHistorial() {}

	public UserProductHistorial(Long historialId, Long productId, Long userId, Date clickDate, Boolean completed) {
		super();
		this.historialId = historialId;
		this.productId = productId;
		this.userId = userId;
		this.clickDate = clickDate;
		this.completed = completed;
	}
	

	public Long getHistorialId() {
		return historialId;
	}

	public void setHistorialId(Long historialId) {
		this.historialId = historialId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
