package japhet.sales.mailing.service.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import japhet.sales.mailing.MailingProperties;
import japhet.sales.mailing.service.IMailingService;
import japhet.sales.mailing.service.IPropertiesReaderService;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@Singleton
@Startup
public class MailingService implements IMailingService {

	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IPropertiesReaderService propertiesReaderService;
	
	//Logic properties
	private final String AUTH = "mail.smtp.auth";
	private final String START_TLS = "mail.smtp.starttls.enable";
	private final String HOST = "mail.smtp.host";
	private final String PORT = "mail.smtp.port";
	
	private Session session;
	
	@PostConstruct
	private void init() {
		final String INFO_MSG = "Initializing MailingService...";
		try {
			logger.info(INFO_MSG);
			//Value props
			final String P_AUTH = getConfigProperty(MailingProperties.AUTH.getKey());
			final String P_START_TLS = getConfigProperty(MailingProperties.START_TLS.getKey());
			final String P_HOST = getConfigProperty(MailingProperties.HOST.getKey());
			final String P_PORT = getConfigProperty(MailingProperties.PORT.getKey());
			final String P_USERNAME = getConfigProperty(MailingProperties.USERNAME.getKey());
			final String P_PASSWORD = getConfigProperty(MailingProperties.PASSWORD.getKey());
			//Logging properties
			Properties properties = new Properties();
			properties.put(AUTH, P_AUTH);
			properties.put(START_TLS, P_START_TLS);
			properties.put(HOST, P_HOST);
			properties.put(PORT, P_PORT);
			
			//Session initialization
			session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(P_USERNAME, P_PASSWORD);
				}
			});
			
			//Session Test
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(P_USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("david.programming@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			logger.info("-----------------> Done <--------------------");
		} catch(Exception e) {
			final String ERROR_MSG = "An error has occurred while initializing the MailingService.";
			logger.fatal(ERROR_MSG, e);
		}
	}
	
	/**
	 * Obtains the value of the specified configuration property if it exists
	 * otherwise returns null.
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private String getConfigProperty(String key) throws Exception {
		return propertiesReaderService.getProperty(key);
	}
}
