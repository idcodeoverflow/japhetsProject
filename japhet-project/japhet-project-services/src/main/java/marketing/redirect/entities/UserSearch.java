package marketing.redirect.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_SEARCH_ID")
	private Integer userSearchId;
	
	@Column(name = "SEARCH_STRING")
	private String searchString;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SEARCH_DATE")
	private Date searchDate;
	
	@JoinColumn(name = "USER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	public UserSearch() {}

	public UserSearch(Integer userSearchId, String searchString, 
			Date searchDate, User user) {
		super();
		this.userSearchId = userSearchId;
		this.searchString = searchString;
		this.searchDate = searchDate;
		this.user = user;
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

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
