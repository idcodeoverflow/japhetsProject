package japhet.sales.model.impl;

import static japhet.sales.data.QueryNames.*;

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

import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_SOCIAL_NETWORK")
@NamedQueries(value = {
		@NamedQuery(name = GET_ALL_SOCIAL_NETWORKS, 
				query = "SELECT s FROM SocialNetwork s"),
		@NamedQuery(name = GET_ALL_AVAILABLE_SOCIAL_NETWORKS,
				query = "SELECT s FROM SocialNetwork s WHERE s.startDate >= CURRENT_DATE AND s.endDate <= CURRENT_DATE")
})
public class SocialNetwork implements IEntity {

	/**
	 * Maven generated
	 */
	private static final long serialVersionUID = -3801224226034302940L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SOCIAL_NETWORK_ID")
	private Short socialNetworkId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "URL")
	private String url;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;
	
	public SocialNetwork() {}

	public SocialNetwork(Short socialNetworkId, String name, 
			String url, Date startDate, Date endDate) {
		super();
		this.socialNetworkId = socialNetworkId;
		this.name = name;
		this.url = url;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Short getSocialNetworkId() {
		return socialNetworkId;
	}

	public void setSocialNetworkId(Short socialNetworkId) {
		this.socialNetworkId = socialNetworkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((socialNetworkId == null) ? 0 : socialNetworkId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocialNetwork other = (SocialNetwork) obj;
		if (socialNetworkId == null) {
			if (other.socialNetworkId != null)
				return false;
		} else if (!socialNetworkId.equals(other.socialNetworkId))
			return false;
		return true;
	}
}
