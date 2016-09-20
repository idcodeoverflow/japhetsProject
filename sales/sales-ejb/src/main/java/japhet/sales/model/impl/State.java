package japhet.sales.model.impl;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import japhet.sales.data.QueryNames;
import japhet.sales.model.IEntity;

@Cacheable(value = true)
@Entity
@Table(name = "TB_STATE")
@NamedQueries({
	@NamedQuery(name = QueryNames.GET_ALL_STATES,
			query = "SELECT s FROM State s")
})
public class State implements IEntity {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 6660719988914434349L;

	@Id
	@Column(name = "STATE_ID")
	private Short stateId;
	
	@Column(name = "NAME")
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE_ID")
	private Set<City> cities;

	public State() {}

	public State(Short stateId, String name, Set<City> cities) {
		super();
		this.stateId = stateId;
		this.name = name;
		this.cities = cities;
	}

	public Short getStateId() {
		return stateId;
	}

	public void setStateId(Short stateId) {
		this.stateId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
	
}
