package com.e_learning.taikhoan;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class SendEmail {
    private static final String EMAIL = "elearningteam1503@gmail.com";
    private static final String PASSWORD = "fsgrxtotvpewmwjy";

    public static int sendEmail(String recipientEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject("Mã xác nhận từ LTDDELearning");
            int code = Integer.parseInt(generateRandomCode());
            message.setText(String.valueOf(code));

            Transport.send(message);
            return code;

        } catch (MessagingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}