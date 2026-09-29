package rw.ba.kigali_barber.invitation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompleteProfileRequestDto(
    @NotBlank String token,
    @NotBlank String username,
    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password
) {
}
