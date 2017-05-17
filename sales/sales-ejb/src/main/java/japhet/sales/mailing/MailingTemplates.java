package japhet.sales.mailing;

public enum MailingTemplates {
	WELCOME_MAIL("META-INF/mailing/templates/welcome.html", "Bienvenido a ProsperAD"),
	WELCOME_COMPANY_MAIL("META-INF/mailing/templates/welcome_company.html", "Bienvenido a ProsperAD"),
	RECOVER_PASSWORD("META-INF/mailing/templates/recover_password.html", "Hemos reiniciado tus credenciales de acceso a ProsperAD"),
	PASSWORD_UPDATED("META-INF/mailing/templates/password_updated.html", "Tus credenciales de acceso a ProsperAD han sido actualizadas."),
	BUYPROOF_UPLOADED("META-INF/mailing/templates/buyproof_uploaded.html", "Hemos recibido tu comprobante de compra."),
	PAYMENT_REQUEST_STARTED("META-INF/mailing/templates/payment_request_started.html", "Hemos recibido tu solicitud de depósito para Afore"),
	BUYPROOF_UPDATED("META-INF/mailing/templates/buyproof_updated.html", "Tu comprobante de compra ha sido actualizado."),
	USER_INFORMATION_UPDATED("META-INF/mailing/templates/user_information_updated.html", "Tu información de adicional ha sido actualizada.");
	
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
