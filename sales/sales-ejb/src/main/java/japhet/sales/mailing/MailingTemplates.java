package japhet.sales.mailing;

public enum MailingTemplates {
	WELCOME_MAIL("META-INF/mailing/templates/welcome.html", "Bienvenido a ProsperAD");
	
	private String path;
	private String subject;
	
	MailingTemplates(String path, String subject) {
		this.path = path;
		this.subject = subject;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getSubject() {
		return this.subject;
	}
}
