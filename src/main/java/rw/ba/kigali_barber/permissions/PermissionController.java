package rw.ba.kigali_barber.permissions;

import java.util.List;
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
import rw.ba.kigali_barber.permissions.dto.PermissionCreateDto;
import rw.ba.kigali_barber.permissions.dto.PermissionResponseDto;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionsService permissionsService;

    public PermissionController(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping
    public ApiResponse<List<PermissionResponseDto>> getAll() {
        List<PermissionResponseDto> permissions = permissionsService.getAll();
        return new ApiResponse<>(true, "Permissions fetched successfully", permissions);
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionResponseDto> getById(@PathVariable UUID id) {
        PermissionResponseDto permission = permissionsService.getById(id);
        return new ApiResponse<>(true, "Permission fetched successfully", permission);
    }

    @PostMapping
    public ApiResponse<PermissionResponseDto> create(@Valid @RequestBody PermissionCreateDto dto) {
        PermissionResponseDto created = permissionsService.create(dto);
        return new ApiResponse<>(true, "Permission created successfully", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<PermissionResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PermissionCreateDto dto) {
        PermissionResponseDto updated = permissionsService.update(id, dto);
        return new ApiResponse<>(true, "Permission updated successfully", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        permissionsService.delete(id);
        return new ApiResponse<>(true, "Permission deleted successfully", null);
    }

    @ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(PermissionNotFoundException ex) {
        ApiResponse<Void> body = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
