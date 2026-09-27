package rw.ba.kigali_barber.permissions;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.ba.kigali_barber.permissions.dto.PermissionCreateDto;
import rw.ba.kigali_barber.permissions.dto.PermissionResponseDto;
import rw.ba.kigali_barber.permissions.entity.PermissionEntity;

@Service
public class PermissionsService {

    private final PermissionRepository permissionRepository;

    public PermissionsService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionResponseDto> getAll() {
        return permissionRepository.findAll()
            .stream()
            .map(PermissionMapper::toResponseDto)
            .toList();
    }

    public PermissionResponseDto getById(UUID id) {
        PermissionEntity entity = findEntityById(id);
        return PermissionMapper.toResponseDto(entity);
    }

    public PermissionResponseDto create(PermissionCreateDto dto) {
        PermissionEntity entity = PermissionMapper.toEntity(dto);
        PermissionEntity saved = permissionRepository.save(entity);
        return PermissionMapper.toResponseDto(saved);
    }

    public PermissionResponseDto update(UUID id, PermissionCreateDto dto) {
        PermissionEntity entity = findEntityById(id);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        PermissionEntity saved = permissionRepository.save(entity);
        return PermissionMapper.toResponseDto(saved);
    }

    public void delete(UUID id) {
        PermissionEntity entity = findEntityById(id);
        permissionRepository.delete(entity);
    }

    private PermissionEntity findEntityById(UUID id) {
        return permissionRepository.findById(id)
            .orElseThrow(() -> new PermissionNotFoundException(id));
    }
}
