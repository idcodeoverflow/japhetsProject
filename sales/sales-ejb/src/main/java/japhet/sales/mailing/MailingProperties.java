package japhet.sales.mailing;

/**
 * @author David Israel Garcia Alcazar
 *
 */
public enum MailingProperties {
	USERNAME					("mailing.credentials.username"),
	PASSWORD					("mailing.credentials.password"),
	SOCKET_FACTORY_PORT			("mailing.properties.factoryPort"),
	SOCKET_FACTORY				("mailing.properties.socketFactory"),
	HOST						("mailing.properties.host"),
	PORT						("mailing.properties.port"),
	AUTH						("mailing.properties.auth");
	
	private String key;
	
	MailingProperties(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
