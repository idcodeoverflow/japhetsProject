package japhet.sales.mailing;

public enum MailingTemplates {
	WELCOME_MAIL("META-INF/mailing/templates/welcome.html", "Bienvenido a ProsperAD"),
	WELCOME_COMPANY_MAIL("META-INF/mailing/templates/welcome_company.html", "Bienvenido a ProsperAD"),
	RECOVER_PASSWORD("META-INF/mailing/templates/recover_password.html", "Hemos reiniciado tus credenciales de acceso a ProsperAD"),
	PASSWORD_UPDATED("META-INF/mailing/templates/password_updated.html", "Tus credenciales de acceso a ProsperAD han sido actualizadas."),
	BUYPROOF_UPLOADED("META-INF/mailing/templates/buyproof_uploaded.html", "Hemos recibido tu comprobante de compra.");
	
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
