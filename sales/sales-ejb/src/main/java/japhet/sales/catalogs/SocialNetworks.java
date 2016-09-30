package japhet.sales.catalogs;

public enum SocialNetworks {
	FACEBOOK	((short)1, "Facebook", "www.facebook.com");
	
	private Short id;
	private String name;
	private String url;
	
	private SocialNetworks(Short id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	public Short getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
}
