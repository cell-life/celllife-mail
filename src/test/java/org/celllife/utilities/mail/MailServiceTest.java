package org.celllife.utilities.mail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@ContextConfiguration({
	"classpath:/META-INF/spring/mail/spring-mail.xml",
	"classpath:/META-INF/spring/mail/spring-application.xml",
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MailServiceTest {

	@Autowired
	private MailService service;
	
	@Test
	@Ignore("actually sends an email")
	public void testHappyDay() throws Exception {
		service.sendEmail("dagmar@cell-life.org", "technical@cell-life.org", "test", "testing new mail utility");
		Thread.sleep(5000); // allow time for the email to send
	}
}
