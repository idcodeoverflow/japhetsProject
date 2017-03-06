package japhet.sales.catalogs;

public enum Statuses {
	ACTIVE 						((short)1, "catalog.status.active"),
	DISABLED 					((short)2, "catalog.status.disabled"),
	VALIDATION_PENDING 			((short)3, "catalog.status.validationPending"),
	VALIDATED 					((short)4, "catalog.status.validated"),
	PAYED 						((short)5, "catalog.status.payed"),
	DENIED 						((short)6, "catalog.status.denied"),
	BLOCKED 					((short)7, "catalog.status.blocked"),
	WAITING_WARRANTY_EXPIRATION ((short)8, "catalog.status.waitingWarrantyExpiration"),
	ON_PAYMENT_REQUEST 			((short)9, "catalog.status.onPaymentRequest");
	
	private Short id;
	private String name;
	
	Statuses(short id, String name) {
		this.id = id;
		this.name = name;
	}

	public Short getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
