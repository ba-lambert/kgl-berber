package rw.ba.kigali_barber.role.dto;

import java.util.List;
import java.util.UUID;

import rw.ba.kigali_barber.permissions.dto.PermissionResponseDto;

public record RoleResponseDto(
    UUID id,
    String name,
    List<PermissionResponseDto> permissions
) {
}
