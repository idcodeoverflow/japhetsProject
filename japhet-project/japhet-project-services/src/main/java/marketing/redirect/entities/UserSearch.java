package marketing.redirect.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_USER_SEARCH")
public class UserSearch implements Serializable {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -233106730587979570L;

	@Id
	@Column(name = "USER_SEARCH_ID")
	private Integer userSearchId;
	
	@Column(name = "SEARCH_STRING")
	private String searchString;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEARCH_DATE")
	private Timestamp searchDate;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	public UserSearch() {}

	public UserSearch(Integer userSearchId, String searchString, 
			Timestamp searchDate, Long userId) {
		super();
		this.userSearchId = userSearchId;
		this.searchString = searchString;
		this.searchDate = searchDate;
		this.userId = userId;
	}

	public Integer getUserSearchId() {
		return userSearchId;
	}

	public void setUserSearchId(Integer userSearchId) {
		this.userSearchId = userSearchId;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Timestamp getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Timestamp searchDate) {
		this.searchDate = searchDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
