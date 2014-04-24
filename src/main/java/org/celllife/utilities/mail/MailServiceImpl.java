package org.celllife.utilities.mail;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	private static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private MailSender mailSender;

	/**
	 * thread pool to manage the sending email threads - this ensures that too many threads don't get executed at once
	 * (for example when 10,000 users are imported)
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

	/**
	 * Runnable class to handle sending the email (in case it takes some time to run)
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
}
