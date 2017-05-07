package japhet.sales.mailing.service.impl;

import java.util.Date;
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

import japhet.sales.except.MailingException;
import japhet.sales.mailing.ContentTypes;
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

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -650744245533176490L;

	@Inject
	private Logger logger;
	
	//EJB
	@EJB
	private IPropertiesReaderService propertiesReaderService;
	
	//Logic properties
	private final String AUTH = "mail.smtp.auth";
	private final String SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
	private final String SOCKET_FACTORY = "mail.smtp.socketFactory.class";
	private final String HOST = "mail.smtp.host";
	private final String PORT = "mail.smtp.port";
	
	//Value props
	private static String P_AUTH;
	private static String P_FACTORY_PORT;
	private static String P_SOCKET_FACTORY;
	private static String P_HOST;
	private static String P_PORT;
	private static String P_USERNAME;
	private static String P_PASSWORD;

	private Session session;
	
	@PostConstruct
	private void init() {
		final String INFO_MSG = "Initializing MailingService...";
		try {
			logger.info(INFO_MSG);
			//Value props
			P_AUTH = getConfigProperty(MailingProperties.AUTH.getKey());
			P_FACTORY_PORT = getConfigProperty(MailingProperties.SOCKET_FACTORY_PORT.getKey());
			P_SOCKET_FACTORY = getConfigProperty(MailingProperties.SOCKET_FACTORY.getKey());
			P_HOST = getConfigProperty(MailingProperties.HOST.getKey());
			P_PORT = getConfigProperty(MailingProperties.PORT.getKey());
			P_USERNAME = getConfigProperty(MailingProperties.USERNAME.getKey());
			P_PASSWORD = getConfigProperty(MailingProperties.PASSWORD.getKey());
			//Logging properties
			Properties properties = new Properties();
			properties.put(AUTH, P_AUTH);
			properties.put(SOCKET_FACTORY_PORT, P_FACTORY_PORT);
			properties.put(SOCKET_FACTORY, P_SOCKET_FACTORY);
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
			message.setText("Testing my mailing service...");

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
	
	/**
	 * This method returns the Session object with 
	 * the valid credentials for the logged in user.
	 * @return
	 */
	public Session getSession() {
		return this.session;
	}
	
	/**
	 * This method sends an email to the specified recipient.
	 * @param subject Subject of the email
	 * @param toEmail TO recipient
	 * @param message Content of the email
	 * @param contentTypes Specifies the content type of the message
	 */
	public void sendMessage(String subject, String toEmail, 
		String message, ContentTypes contentTypes) throws MailingException {
		try {
			final String INFO_MSG = String.format("Sending email: %s %s...", toEmail, subject);
			logger.info(INFO_MSG);
			
			//Creates a new e-mail message
	        Message msg = new MimeMessage(session);
	 
	        msg.setFrom(new InternetAddress(P_USERNAME));
	        InternetAddress[] toAddresses = { new InternetAddress(toEmail) };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	        //Set plain text message
	        msg.setContent(message, contentTypes.getMimeType());
	 
	        //Sends the e-mail
	        Transport.send(msg);
		} catch(Exception e) {
			final String ERROR_MSG = String
					.format("An exception has occurred while sending an email: %s %s", toEmail, subject);
			logger.fatal(ERROR_MSG, e);
			throw new MailingException(ERROR_MSG, e);
		}
	}
}
