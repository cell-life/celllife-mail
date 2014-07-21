package org.celllife.utilities.mail;

import java.io.File;

import org.springframework.mail.MailException;

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
	 * @throws MailException if any problem occurs while creating the email
	 */
	void sendEmail(String to, String from, String subject, String text) throws MailException;

	/**
	 * Sends an email using the default from address
	 * 
     * @param to String email address to which the email is sent 
     * @param subject String subject of the email
     * @param text String email body text
     * @throws MailException if any problem occurs while creating the email
	 */
    void sendEmail(String to, String subject, String text) throws MailException;
    
    /**
     * Sends an email with an attachment
     * 
     * @param to String email address to which the email is sent
     * @param from String email address from which the email is sent 
     * @param subject String subject of the email
     * @param text String email body text
     * @param attachment File attachment, must not be null
     * @throws MailException if any problem occurs while creating the email
     */
    void sendEmail(String to, String from, String subject, String text, File attachment) throws MailException;

    /**
     * Sends an email with an attachment from the default from address
     * 
     * @param to String email address to which the email is sent 
     * @param subject String subject of the email
     * @param text String email body text
     * @param attachment File attachment, must not be null
     * @throws MailException if any problem occurs while creating the email
     */
    void sendEmail(String to, String subject, String text, File attachment) throws MailException;

    /**
     * Set the default from address for this MailService
     *
     * @param from String email address
     */
    void setFrom(String from);

}
