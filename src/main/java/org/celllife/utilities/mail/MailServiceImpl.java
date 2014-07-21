package org.celllife.utilities.mail;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * A default from address for all email sent using this service
     */
    private String from;

    /**
     * thread pool to manage the sending email threads - this ensures that too
     * many threads don't get executed at once (for example when 10,000 users
     * are imported)
     */
    ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 10, 3600, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @Override
    public void sendEmail(String to, String from, String subject, String text) throws MailException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(from);

        tpe.execute(new SendEmailThread(msg));
    }

    @Override
    public void sendEmail(String to, String subject, String text) throws MailException {
        sendEmail(to, from, subject, text);
    }

    @Override
    public void sendEmail(String to, String from, String subject, String text, File attachment) throws MailException {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(attachment);
            helper.addAttachment(file.getFilename(), file);

            tpe.execute(new SendEmailWithAttachmentThread(message));
        } catch (MessagingException e) {
            throw new MailPreparationException("Could not create an email to '" + to + "' with an attachment '"
                    + attachment + "'.", e);
        }
    }

    @Override
    public void sendEmail(String to, String subject, String text, File attachment) throws MailException {
        sendEmail(to, from, subject, text, attachment);
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Runnable class to handle sending the email (in case it takes some time to
     * run)
     */
    class SendEmailThread implements Runnable {
        SimpleMailMessage msg;

        public SendEmailThread(SimpleMailMessage msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if (log.isDebugEnabled()) {
                log.debug("Sending email message from:" + msg.getFrom() + " to:" + msg.getTo()[0] + " subject:"
                        + msg.getSubject());
            }
            mailSender.send(msg);
        }
    }

    /**
     * Runnable class to handle sending the email (in case it takes some time to
     * run)
     */
    class SendEmailWithAttachmentThread implements Runnable {
        MimeMessage msg;

        public SendEmailWithAttachmentThread(MimeMessage msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if (log.isDebugEnabled()) {
                log.debug("Sending email message with attachment: " + msg);
            }
            mailSender.send(msg);
        }
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
