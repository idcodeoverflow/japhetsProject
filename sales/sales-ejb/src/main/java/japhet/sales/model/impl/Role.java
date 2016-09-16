package japhet.sales.model.impl;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import japhet.sales.data.QueryNames;
import japhet.sales.model.IEntity;

@Entity
@Cacheable(value = true)
@Table(name = "TB_ROLE")
@NamedQueries({
	@NamedQuery(name = QueryNames.GET_ALL_AVAILABLE_ROLES,
			query = "SELECT r FROM Role r WHERE r.startDate <= CURRENT_DATE AND r.endDate >= CURRENT_DATE"),
	@NamedQuery(name = QueryNames.GET_ALL_ROLES,
			query = "SELECT r FROM Role r")
})
public class Role implements IEntity {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -5589899933409461137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STATUS_ID")
	private Short roleId;
	
	@Column(name = "NAME")
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;
	
	public Role() {}

	public Role(Short roleId, String name, Date startDate, Date endDate) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Short getRoleId() {
		return roleId;
	}

	public void setRoleId(Short roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
