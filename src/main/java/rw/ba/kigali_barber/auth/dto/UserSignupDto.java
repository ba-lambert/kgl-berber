package rw.ba.kigali_barber.auth.dto;

import java.util.UUID;

public record UserSignupDto(
    String username,
    String email,
    UUID role
) {
}