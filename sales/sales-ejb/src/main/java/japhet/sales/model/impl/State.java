package japhet.sales.model.impl;

import java.util.SortedSet;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.PostLoad;
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
public class State implements IEntity, Comparable<State> {
	
	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 6660719988914434349L;

	@Id
	@Column(name = "STATE_ID")
	private Short stateId;
	
	@OrderColumn
	@Column(name = "NAME")
	private String name;

	@OrderBy(value = "NAME")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE_ID")
	private SortedSet<City> cities;

	@PostLoad
	public void init() {
		//Convert to uppercase all names
		this.name = this.name.toUpperCase();
	}
	
	public State() {}

	public State(Short stateId, String name, SortedSet<City> cities) {
		super();
		this.stateId = stateId;
		this.name = name.toUpperCase();
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
		this.name = name.toUpperCase();
	}

	public SortedSet<City> getCities() {
		return cities;
	}

	public void setCities(SortedSet<City> cities) {
		this.cities = cities;
	}

	@Override
	public int compareTo(State o) {
		return name.compareToIgnoreCase(o.getName());
	}
	
}
