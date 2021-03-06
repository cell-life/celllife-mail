This project provides mail sending capabilities. 

To use
======

Step 1: Add the celllife-mail dependency in your pom.xml
*root pom.xml*

```xml
    <dependency>
      <groupId>org.celllife.mail</groupId>
      <artifactId>celllife-mail</artifactId>
      <version>1.0.1</version>
    </dependency>
```

*webapp pom.xml*

```xml
    <dependency>
      <groupId>org.celllife.mail</groupId>
      <artifactId>celllife-mail</artifactId>
    </dependency>
```

Step 2: Create a file called spring-mail.xml under your META-INF/spring folder. There are two options here:

A. If you are going to Autowire the MailService using an annotation and are going to specify the from address whenever you use the MailService

```xml
<?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

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

B. If you are going to wire the MailService using the application context XML and wish to specify the default from address for all emails sent by the MailService

```xml
<?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

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

      <bean id="mailService" class="org.celllife.utilities.mail.MailServiceImpl">
          <property name="mailSender" ref="mailSender" />
          <property name="from" value="${mailSender.from}" />
      </bean>

    </beans>
```

Step 3: add the properties referenced above into your application.properties

```bash
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
    mailService.sendEmail("dagmar@cell-life.org", "technical@cell-life.org", "test", "this is a text email");
    mailService.sendEmail("dagmar@cell-life.org", "technical@cell-life.org", "test", "this is a text email with an attachment", attachment);
    mailService.sendEmail("dagmar@cell-life.org", "test", "this is a text email where we use the default from address");
```
