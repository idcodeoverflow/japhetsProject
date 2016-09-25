package japhet.sales.catalogs;

public enum Roles {
	ADMINISTRATOR	((short)1, "Administrator"),
	COMPANY			((short)2, "Company"),
	USER			((short)3, "User");
	
	private Short id;
	private String name;
	
	Roles(Short id, String name) {
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
