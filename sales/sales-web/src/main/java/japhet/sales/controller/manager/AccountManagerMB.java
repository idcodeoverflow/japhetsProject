package japhet.sales.controller.manager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import japhet.sales.controller.GenericMB;

@ManagedBean
@ViewScoped
public class AccountManagerMB extends GenericMB {

	/**
	 * Maven generated
	 */
	private static final long serialVersionUID = -5288053365897420779L;

	public List<String> getDeposits() {
		List<String> deposits = new ArrayList<>();
		deposits.add("182646");
		deposits.add("182878");
		deposits.add("182656");
		deposits.add("182686");
		return deposits;
	}
	
}
