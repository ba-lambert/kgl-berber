package rw.ba.kigali_barber.role;

import rw.ba.kigali_barber.permissions.PermissionMapper;
import rw.ba.kigali_barber.role.dto.RoleRequestDto;
import rw.ba.kigali_barber.role.dto.RoleResponseDto;

public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleEntity toEntity(RoleRequestDto dto) {
        RoleEntity entity = new RoleEntity();
        entity.setRole(dto.getName());
        return entity;
    }

    public static RoleResponseDto toResponseDto(RoleEntity entity) {
        return new RoleResponseDto(
            entity.getId(),
            entity.getRole(),
            entity.getPermissions()
                .stream()
                .map(PermissionMapper::toResponseDto)
                .toList()
        );
    }
}
