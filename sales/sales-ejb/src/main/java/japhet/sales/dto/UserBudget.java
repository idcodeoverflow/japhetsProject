package japhet.sales.dto;

public class UserBudget {

	private Double amountReadyToPay;
	private Double amountOnHold;
	
	public UserBudget() {
		this.amountOnHold = 0.0;
		this.amountReadyToPay = 0.0;
	}
	
	public UserBudget(Double amountReadyToPay, Double amountOnHold) {
		this.amountReadyToPay = amountReadyToPay;
		this.amountOnHold = amountOnHold;
	}

	public Double getAmountReadyToPay() {
		return amountReadyToPay;
	}

	public void setAmountReadyToPay(Double amountReadyToPay) {
		this.amountReadyToPay = amountReadyToPay;
	}

	public Double getAmountOnHold() {
		return amountOnHold;
	}

	public void setAmountOnHold(Double amountOnHold) {
		this.amountOnHold = amountOnHold;
	}
}
