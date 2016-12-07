package japhet.sales.catalogs;

public enum CompanyTypes {
	GOODS((short) 1, "GOODS"),
	SERVICES((short) 2, "SERVICES");
	
	private short companyTypeId;
	private String name;
	
	CompanyTypes(short companyTypeId, String name) {
		this.companyTypeId = companyTypeId;
		this.name= name;
	}

	public short getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(short companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
