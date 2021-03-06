package japhet.sales.mailing.service;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.Local;
import javax.mail.Session;

import japhet.sales.except.MailingException;
import japhet.sales.mailing.ContentTypes;
import japhet.sales.mailing.MailingTemplates;

@Local
public interface IMailingService extends Serializable {

	public Session getSession();
	
	public void sendMessage(String subject, String toEmail, MailingTemplates templates, 
			ContentTypes contentTypes, Map<String, Object> params) throws MailingException;
}
