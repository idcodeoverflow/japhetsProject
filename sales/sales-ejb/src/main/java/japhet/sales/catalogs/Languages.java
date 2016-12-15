package japhet.sales.catalogs;

public enum Languages {
	SPANISH_DEFAULT("Spanish(Default)", "");
	
	private String name;
	private String suffix;
	private static final String baseName = "messages%s%s.properties";
	
	private Languages(String name, String suffix) {
		this.name = name;
		this.suffix = suffix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public static String getBasename() {
		return baseName;
	}
	
	public String getI18NMessagesFile() {
		final String TOKEN = ((!"".equals(suffix)) ? "-" : "");
		return String.format(baseName, TOKEN, suffix);
	}
}
