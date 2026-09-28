package rw.ba.kigali_barber.role.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class RoleRequestDto {

    @NotBlank
    private String name;

    private Set<UUID> permissionIds = new HashSet<>();

    public RoleRequestDto() {
    }

    public RoleRequestDto(String name, Set<UUID> permissionIds) {
        this.name = name;
        this.permissionIds = permissionIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<UUID> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
