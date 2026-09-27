package rw.ba.kigali_barber.permissions.dto;

import java.util.UUID;

public record PermissionResponseDto(
    UUID id,
    String name,
    String description
) {
}
