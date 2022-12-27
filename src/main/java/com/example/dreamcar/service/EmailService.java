package com.example.dreamcar.service;

import com.example.dreamcar.models.CarComponent;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

@Service
public class EmailService {

    private static String email = "gestionare.proiecte.web@gmail.com";
    private static String password = "gucpmmtlpjsvnxcj";

    /* Private methods (session initialization) : */
    private static Properties getProperties() {
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return props;
    }
    private static Session getSession(Properties props) {
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailService.email, EmailService.password);
            }
        });
    }
    public void sendEmail(String email, CarComponent carComponent) {
        Properties props = getProperties();
        Session session = getSession(props);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(EmailService.email));
            message.setSubject("Congratulations! You won the auction!");

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setText("You won the auction for " + carComponent.getName() + "!");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
