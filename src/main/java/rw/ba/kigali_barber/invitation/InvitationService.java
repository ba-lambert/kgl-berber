package rw.ba.kigali_barber.invitation;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ba.kigali_barber.auth.EmailService;
import rw.ba.kigali_barber.auth.InvalidTokenException;
import rw.ba.kigali_barber.invitation.dto.CompleteProfileRequestDto;
import rw.ba.kigali_barber.invitation.dto.InviteRequestDto;
import rw.ba.kigali_barber.role.RoleEntity;
import rw.ba.kigali_barber.role.RoleNotFoundException;
import rw.ba.kigali_barber.role.RoleRepository;
import rw.ba.kigali_barber.user.UserAlreadyExistsException;
import rw.ba.kigali_barber.user.UserEntity;
import rw.ba.kigali_barber.user.UserRepository;

@Service
public class InvitationService {

    private static final long INVITATION_TTL_SECONDS = 30 * 60;

    private final InvitationRepository invitationRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final String invitationUrl;

    public InvitationService(
        InvitationRepository invitationRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        EmailService emailService,
        @Value("${app.invitation-url}") String invitationUrl
    ) {
        this.invitationRepository = invitationRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.invitationUrl = invitationUrl;
    }

    @Transactional
    public String invite(InviteRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException("Email already registered: " + dto.email());
        }

        RoleEntity role = roleRepository.findByRole(dto.role())
            .orElseThrow(() -> new RoleNotFoundException(dto.role()));

        InvitationEntity invitation = new InvitationEntity();
        invitation.setEmail(dto.email());
        invitation.setRole(role);
        invitation.setToken(UUID.randomUUID().toString());
        invitation.setExpiresAt(Instant.now().plusSeconds(INVITATION_TTL_SECONDS));
        invitation.setAccepted(false);
        invitationRepository.save(invitation);

        String link = invitationUrl + "?token=" + invitation.getToken();
        emailService.sendInvitationEmail(dto.email(), link, role.getRole());

        return "Invitation sent";
    }

    @Transactional
    public String completeProfile(CompleteProfileRequestDto dto) {
        InvitationEntity invitation = invitationRepository.findByToken(dto.token())
            .orElseThrow(() -> new InvalidTokenException("Invalid or expired invitation"));

        if (invitation.isAccepted() || invitation.isExpired()) {
            throw new InvalidTokenException("Invalid or expired invitation");
        }

        if (userRepository.existsByUsername(dto.username())) {
            throw new UserAlreadyExistsException("Username already taken: " + dto.username());
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.username());
        user.setEmail(invitation.getEmail());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(invitation.getRole());
        userRepository.save(user);

        invitation.setAccepted(true);
        invitationRepository.save(invitation);

        return "Profile completed successfully";
    }
}
