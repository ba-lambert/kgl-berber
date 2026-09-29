package rw.ba.kigali_barber.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public SmtpEmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject("Reset your Kigali Barber password");
        message.setText("Use the link below to reset your password. This link expires in 30 minutes.\n\n" + resetLink);
        mailSender.send(message);
    }

    @Override
    public void sendInvitationEmail(String to, String invitationLink, String role) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject("You've been invited to Kigali Barber");
        message.setText(
            "You've been invited to join as " + role + ". Use the link below to complete your profile."
                + " This link expires in 30 minutes.\n\n" + invitationLink
        );
        mailSender.send(message);
    }
}
