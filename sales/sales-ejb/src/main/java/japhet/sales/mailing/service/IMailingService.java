package japhet.sales.mailing.service;

import java.io.Serializable;

import javax.ejb.Local;
import javax.mail.Session;

import japhet.sales.except.MailingException;
import japhet.sales.mailing.ContentTypes;

@Local
public interface IMailingService extends Serializable {

	public Session getSession();
	
	public void sendMessage(String subject, String toEmail, 
		String message, ContentTypes contentTypes) throws MailingException;
}
