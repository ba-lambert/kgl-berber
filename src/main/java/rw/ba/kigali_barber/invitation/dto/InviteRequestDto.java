package rw.ba.kigali_barber.invitation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteRequestDto(
    @NotBlank @Email String email,
    @NotBlank String role
) {
}
