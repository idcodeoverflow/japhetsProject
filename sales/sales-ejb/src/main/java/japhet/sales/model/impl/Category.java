package japhet.sales.model.impl;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.data.QueryNames;
import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_CATEGORY")
@NamedQueries ({
	
	@NamedQuery(name = QueryNames.GET_ALL_AVAILABLE_CATEGORIES, 
		query = "SELECT c FROM Category c WHERE c.availableDate <= CURRENT_DATE AND c.endDate >= CURRENT_DATE"),
	@NamedQuery(name = QueryNames.GET_ALL_CATEGORIES, 
		query = "SELECT c FROM Category c")
})
public class Category implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -2206452937897143174L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private Short categoryId;
	
	@Column(name = "NAME")
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_TIME")
	private Date creationTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AVAILABLE_DATE")
	private Date availableDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;
	
	private transient Boolean checked;
	
	public Category() {}

	public Category(Short categoryId, String name, Date creationTime, 
			Date availableDate, Date endDate, Boolean checked) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.creationTime = creationTime;
		this.availableDate = availableDate;
		this.endDate = endDate;
		this.checked = checked;
	}

	public Short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Short categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
