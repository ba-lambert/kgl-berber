package rw.ba.kigali_barber.auth;

public interface EmailService {

    void sendPasswordResetEmail(String to, String resetLink);

    void sendInvitationEmail(String to, String invitationLink, String role);
}
