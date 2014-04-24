package org.celllife.utilities.mail;

/**
 * Service which can be used to send emails
 */
public interface MailService {

	/**
	 * Sends an email (on a thread)
	 * 
	 * @param to String email address to which the email is sent
	 * @param from String email address from which the email is sent 
	 * @param subject String subject of the email
	 * @param text String email body text
	 */
	void sendEmail(String to, String from, String subject, String text);
}
