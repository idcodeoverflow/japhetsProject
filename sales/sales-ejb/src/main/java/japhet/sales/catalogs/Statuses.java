package japhet.sales.catalogs;

public enum Statuses {
	ACTIVE 						((short)1, "ACTIVE"),
	DISABLED 					((short)2, "DISABLED"),
	VALIDATION_PENDING 			((short)3, "VALIDATION_PENDING"),
	VALIDATED 					((short)4, "VALIDATED"),
	PAYED 						((short)5, "PAYED"),
	DENIED 						((short)6, "DENIED"),
	BLOCKED 					((short)7, "BLOCKED"),
	WAITING_WARRANTY_EXPIRATION ((short)8, "WAITING_WARRANTY_EXPIRATION"),
	READY_FOR_PAY_REQUEST 		((short)9, "READY_FOR_PAY_REQUEST");
	
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
