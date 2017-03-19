package japhet.sales.model.impl;

import java.sql.Time;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import japhet.sales.model.IEntity;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */

@Cacheable(value = true)
@Entity
@Table(name = "TB_USER_INFORMATION")
public class UserInformation implements IEntity {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -5940646716509735326L;
	
	@Id
	@Column(name = "USER_ID",
			nullable = false,
			unique = true)
	private Long userId;
	
	@Column(name = "STREET",
			nullable = true)
	private String street;
	
	@Column(name = "EXT_NUMBER",
			nullable = true)
	private String extNumber;
	
	@Column(name = "INT_NUMBER",
			nullable = true)
	private String intNumber;
	
	@Column(name = "SETTLEMENT",
			nullable = true)
	private String settlement;
	
	@Column(name = "TEL_NUMBER",
			nullable = true)
	private String telNumber;
	
	@Column(name = "CEL_NUMBER",
			nullable = true)
	private String celNumber;
	
	@Column(name = "CONTACT_SCHD_START_TIME",
			nullable = true)
	private Time contactSchdStartTime;
	
	@Column(name = "CONTACT_SCHD_END_TIME",
			nullable = true)
	private Time contactSchdEndTime;
	
	/**
	 * Default constructor.
	 */
	public UserInformation() {}

	/**
	 * Constructor using fields.
	 * @param userId
	 * @param street
	 * @param extNumber
	 * @param intNumber
	 * @param settlement
	 * @param telNumber
	 * @param celNumber
	 * @param contactSchdStartTime
	 * @param contactSchdEndTime
	 */
	public UserInformation(Long userId, String street, 
			String extNumber, String intNumber, String settlement,
			String telNumber, String celNumber, Time contactSchdStartTime, 
			Time contactSchdEndTime) {
		super();
		this.userId = userId;
		this.street = street;
		this.extNumber = extNumber;
		this.intNumber = intNumber;
		this.settlement = settlement;
		this.telNumber = telNumber;
		this.celNumber = celNumber;
		this.contactSchdStartTime = contactSchdStartTime;
		this.contactSchdEndTime = contactSchdEndTime;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the extNumber
	 */
	public String getExtNumber() {
		return extNumber;
	}

	/**
	 * @param extNumber the extNumber to set
	 */
	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}

	/**
	 * @return the intNumber
	 */
	public String getIntNumber() {
		return intNumber;
	}

	/**
	 * @param intNumber the intNumber to set
	 */
	public void setIntNumber(String intNumber) {
		this.intNumber = intNumber;
	}

	/**
	 * @return the settlement
	 */
	public String getSettlement() {
		return settlement;
	}

	/**
	 * @param settlement the settlement to set
	 */
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	/**
	 * @return the telNumber
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * @param telNumber the telNumber to set
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	/**
	 * @return the celNumber
	 */
	public String getCelNumber() {
		return celNumber;
	}

	/**
	 * @param celNumber the celNumber to set
	 */
	public void setCelNumber(String celNumber) {
		this.celNumber = celNumber;
	}

	/**
	 * @return the contactSchdStartTime
	 */
	public Time getContactSchdStartTime() {
		return contactSchdStartTime;
	}

	/**
	 * @param contactSchdStartTime the contactSchdStartTime to set
	 */
	public void setContactSchdStartTime(Time contactSchdStartTime) {
		this.contactSchdStartTime = contactSchdStartTime;
	}

	/**
	 * @return the contactSchdEndTime
	 */
	public Time getContactSchdEndTime() {
		return contactSchdEndTime;
	}

	/**
	 * @param contactSchdEndTime the contactSchdEndTime to set
	 */
	public void setContactSchdEndTime(Time contactSchdEndTime) {
		this.contactSchdEndTime = contactSchdEndTime;
	}
}
