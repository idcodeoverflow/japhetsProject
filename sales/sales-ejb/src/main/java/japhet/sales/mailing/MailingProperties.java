package japhet.sales.mailing;

/**
 * @author David Israel Garcia Alcazar
 *
 */
public enum MailingProperties {
	USERNAME		("mailing.credentials.username"),
	PASSWORD		("mailing.credentials.password"),
	AUTH			("mailing.properties.auth"),
	START_TLS		("mailing.properties.starttls"),
	HOST			("mailing.properties.host"),
	PORT			("mailing.properties.port");
	
	private String key;
	
	MailingProperties(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
