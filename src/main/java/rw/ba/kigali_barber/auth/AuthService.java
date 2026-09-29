package rw.ba.kigali_barber.auth;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ba.kigali_barber.auth.dto.ForgotPasswordRequestDto;
import rw.ba.kigali_barber.auth.dto.LoginRequestDto;
import rw.ba.kigali_barber.auth.dto.ResetPasswordRequestDto;
import rw.ba.kigali_barber.auth.security.CustomUserDetailsService;
import rw.ba.kigali_barber.auth.security.JwtService;
import rw.ba.kigali_barber.user.UserEntity;
import rw.ba.kigali_barber.user.UserRepository;

@Service
public class AuthService {

    private static final long RESET_TOKEN_TTL_SECONDS = 30 * 60;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final String resetPasswordUrl;

    public AuthService(
        UserRepository userRepository,
        PasswordResetTokenRepository resetTokenRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        CustomUserDetailsService userDetailsService,
        JwtService jwtService,
        EmailService emailService,
        @Value("${app.reset-password-url}") String resetPasswordUrl
    ) {
        this.userRepository = userRepository;
        this.resetTokenRepository = resetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.resetPasswordUrl = resetPasswordUrl;
    }

    public String login(LoginRequestDto dto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.username());
        return jwtService.generateToken(userDetails);
    }

    @Transactional
    public String forgotPassword(ForgotPasswordRequestDto dto) {
        userRepository.findByEmail(dto.email()).ifPresent(user -> {
            PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity();
            resetToken.setToken(UUID.randomUUID().toString());
            resetToken.setUser(user);
            resetToken.setExpiresAt(Instant.now().plusSeconds(RESET_TOKEN_TTL_SECONDS));
            resetToken.setUsed(false);
            resetTokenRepository.save(resetToken);

            String link = resetPasswordUrl + "?token=" + resetToken.getToken();
            emailService.sendPasswordResetEmail(user.getEmail(), link);
        });

        return "If an account with that email exists, a reset link has been sent";
    }

    @Transactional
    public String resetPassword(ResetPasswordRequestDto dto) {
        PasswordResetTokenEntity resetToken = resetTokenRepository.findByToken(dto.token())
            .orElseThrow(() -> new InvalidTokenException("Invalid or expired reset token"));

        if (resetToken.isUsed() || resetToken.isExpired()) {
            throw new InvalidTokenException("Invalid or expired reset token");
        }

        UserEntity user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        resetTokenRepository.save(resetToken);

        return "Password reset successfully";
    }
}
