package rw.ba.kigali_barber.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank String username,
    @NotBlank String password
) {
}
