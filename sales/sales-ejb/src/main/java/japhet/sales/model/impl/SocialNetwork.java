package japhet.sales.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import japhet.sales.model.IEntity;

@Entity
@Table(name = "TB_CATEGORY")
public class SocialNetwork implements IEntity {

	/**
	 * Maven generated
	 */
	private static final long serialVersionUID = -3801224226034302940L;

	@Id
	@Column(name = "SOCIAL_NETWORK_ID")
	private Short socialNetworkId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "URL")
	private String url;
	
	public SocialNetwork() {}

	public SocialNetwork(Short socialNetworkId, String name, String url) {
		super();
		this.socialNetworkId = socialNetworkId;
		this.name = name;
		this.url = url;
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
