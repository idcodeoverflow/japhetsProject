package japhet.sales.catalogs;

public enum Statuses {
	ACTIVE ((short)1, "Active"),
	DISABLED ((short)2, "Disabled");
	
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
