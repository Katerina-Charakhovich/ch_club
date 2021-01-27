package com.charakhovich.club.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger(MailSender.class);
    private static final MailSender INSTANCE = new MailSender();
    private static final String MAIL_PROPERTIES = "mail.properties";
    private static final String USER_NAME = "mail.smtp.user";
    private static final String USER_PASSWORD = "mail.smtp.password";
 //   private Session session;
    private Properties mailProperties = new Properties();

    public static MailSender getINSTANCE() {
        return INSTANCE;
    }

    private MailSender() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(MAIL_PROPERTIES);
            mailProperties.load(input);
            mailProperties.put("mail.smtp.ssl.trust", "*");
        } catch (IOException e) {
            logger.log(Level.ERROR, "Impossible to send a message. Mail service has incorrect configuration");
        }
    }

    public boolean sendMessage(String sendTo, String subject, String text) {
        boolean isSend = false;
        try {
            String username = mailProperties.getProperty(USER_NAME);
            String userPassword = mailProperties.getProperty(USER_PASSWORD);
            Session session = Session.getDefaultInstance(mailProperties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username,userPassword);
                        }
                    });
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setText(text);
            message.setSubject(subject);
            Transport.send(message);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.log(Level.ERROR, e);
        }finally {
        }
        return isSend;
    }
}

