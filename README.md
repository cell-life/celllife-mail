This project provides mail sending capabilities. 

To use celllife-mail
====================

Step 1: Add the celllife-mail dependency in your pom.xml
*root pom.xml*
```
<dependency>
  <groupId>org.celllife.mail</groupId>
  <artifactId>celllife-mail</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

*webapp pom.xml*
```
<dependency>
  <groupId>org.celllife.mail</groupId>
  <artifactId>celllife-mail</artifactId>
</dependency>
```

Step 2: Create a file called spring-mail.xml under your META-INF/spring folder
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <context:component-scan base-package="org.celllife.utilities.mail"/>
    
    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
    <property name="username" value="${mailSender.username}"/>
    	<property name="password" value="${mailSender.password}"/>
		<property name="host" value="${mailSender.host}"/>
		<property name="protocol" value="${mailSender.protocol}"/>
		<property name="port" value="${mailSender.port}"/>
		<property name="javaMailProperties">
			<props>
				<!-- Use SMTP-AUTH to authenticate to SMTP server -->
				<prop key="mail.smtp.auth">${mailSender.smtp.auth}</prop>
				<!-- Use TLS to encrypt communication with SMTP server -->
				<prop key="mail.smtp.starttls.enable">${mailSender.smtp.starttls.enable}</prop>
			</props>
		</property>	
	</bean>

</beans>
```

Step 3: add the properties referenced above into your application.properties
```bash
# Email
mailSender.username=technical@cell-life.org
mailSender.password=xxxxxxxxxxxxx
mailSender.host=smtp.gmail.com
mailSender.protocol=smtps
mailSender.from=technical@cell-life.org
mailSender.port=465
mailSender.smtp.auth=false
mailSender.smtp.starttls.enable=false
```

Step 4: Autowire the MailService class where you want to use it
```java
@Autowired
private MailService mailService
```

Step 5: Use it
```java
mailService.sendEmail('dagmar@cell-life.org', 'technical@cell-life.org', 'test', 'this is a text email subject');
```