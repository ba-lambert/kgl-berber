package rw.ba.kigali_barber.permissions;

import rw.ba.kigali_barber.permissions.dto.PermissionCreateDto;
import rw.ba.kigali_barber.permissions.dto.PermissionResponseDto;
import rw.ba.kigali_barber.permissions.entity.PermissionEntity;

public class PermissionMapper {

    private PermissionMapper() {
    }

    public static PermissionEntity toEntity(PermissionCreateDto dto) {
        PermissionEntity entity = new PermissionEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static PermissionResponseDto toResponseDto(PermissionEntity entity) {
        return new PermissionResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
        );
    }
}
