package japhet.sales.model.impl;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;

import japhet.sales.data.QueryNames;
import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_CITY")
@NamedQueries({
	@NamedQuery(name = QueryNames.GET_ALL_CITIES,
			query = "SELECT c FROM City c")
})
public class City implements IEntity, Comparable<City> {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 8646246285431719837L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CITY_ID")
	private Short cityId;
	
	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	private State state;

	@PostLoad
	public void init() {
		//Convert to uppercase all names
		this.name = this.name.toUpperCase();
	}

	public City() {}

	public City(Short cityId, String name, State state) {
		super();
		this.cityId = cityId;
		this.name = name.toUpperCase();
		this.state = state;
	}
	
	public Short getCityId() {
		return cityId;
	}

	public void setCityId(Short cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
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
		City other = (City) obj;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		return true;
	}

	@Override
	public int compareTo(City o) {
		return name.compareToIgnoreCase(o.getName());
	}
}
