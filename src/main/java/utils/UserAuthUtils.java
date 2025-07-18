package utils;

import constant.UtilsConstant;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;
import java.util.Properties;

public class UserAuthUtils {
    private static final String senderEmail = PropertiesUtils.get("config", "mail.username");
    private static final String senderPassword = PropertiesUtils.get("config", "mail.password");

    private static final SecureRandom secureRandom = new SecureRandom();

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public static void sendResetEmail(String to, String token, String baseUrl) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Password Reset Request");
        String resetLink = baseUrl + "/reset?token=" + token;
        message.setText("Click this link to reset your password: " + resetLink);
        Transport.send(message);
    }

    public static void sendOTPEmail(String to, String otp) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nThis code is valid for 5 minutes.");
        Transport.send(message);
    }

    public static boolean isValidOtp(String sessionOtp, Long otpTime) {
        if (sessionOtp == null || otpTime == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        return now - otpTime <= UtilsConstant.OTP_EXPIRY_TIME;
    }

    public static String generateOtp() {
        int otp = 100000 + secureRandom.nextInt(900000); //6-digit
        return String.valueOf(otp);
    }

}
