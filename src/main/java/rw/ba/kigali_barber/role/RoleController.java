package rw.ba.kigali_barber.role;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rw.ba.kigali_barber.common.response.ApiResponse;
import rw.ba.kigali_barber.permissions.PermissionNotFoundException;
import rw.ba.kigali_barber.role.dto.RoleRequestDto;
import rw.ba.kigali_barber.role.dto.RoleResponseDto;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ApiResponse<List<RoleResponseDto>> getAll() {
        List<RoleResponseDto> roles = roleService.getAll();
        return new ApiResponse<>(true, "Roles fetched successfully", roles);
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleResponseDto> getById(@PathVariable UUID id) {
        RoleResponseDto role = roleService.getById(id);
        return new ApiResponse<>(true, "Role fetched successfully", role);
    }

    @PostMapping
    public ApiResponse<RoleResponseDto> create(@Valid @RequestBody RoleRequestDto dto) {
        RoleResponseDto created = roleService.create(dto);
        return new ApiResponse<>(true, "Role created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleResponseDto> update(@PathVariable UUID id, @Valid @RequestBody RoleRequestDto dto) {
        RoleResponseDto updated = roleService.update(id, dto);
        return new ApiResponse<>(true, "Role updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        roleService.delete(id);
        return new ApiResponse<>(true, "Role deleted successfully", null);
    }

    @PostMapping("/{id}/permissions")
    public ApiResponse<RoleResponseDto> assignPermissions(@PathVariable UUID id, @RequestBody Set<UUID> permissionIds) {
        RoleResponseDto updated = roleService.assignPermissions(id, permissionIds);
        return new ApiResponse<>(true, "Permissions assigned successfully", updated);
    }

    @DeleteMapping("/{id}/permissions/{permissionId}")
    public ApiResponse<RoleResponseDto> revokePermission(@PathVariable UUID id, @PathVariable UUID permissionId) {
        RoleResponseDto updated = roleService.revokePermission(id, permissionId);
        return new ApiResponse<>(true, "Permission revoked successfully", updated);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotFound(RoleNotFoundException ex) {
        ApiResponse<Void> body = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePermissionNotFound(PermissionNotFoundException ex) {
        ApiResponse<Void> body = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
