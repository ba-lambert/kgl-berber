package rw.ba.kigali_barber.invitation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rw.ba.kigali_barber.common.response.ApiResponse;
import rw.ba.kigali_barber.invitation.dto.CompleteProfileRequestDto;
import rw.ba.kigali_barber.invitation.dto.InviteRequestDto;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('INVITE_USER')")
    public ApiResponse<String> invite(@Valid @RequestBody InviteRequestDto dto) {
        return new ApiResponse<>(true, "success", invitationService.invite(dto));
    }

    @PostMapping("/complete")
    public ApiResponse<String> completeProfile(@Valid @RequestBody CompleteProfileRequestDto dto) {
        return new ApiResponse<>(true, "success", invitationService.completeProfile(dto));
    }
}
