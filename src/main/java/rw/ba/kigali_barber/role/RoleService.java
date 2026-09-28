package rw.ba.kigali_barber.role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ba.kigali_barber.permissions.PermissionNotFoundException;
import rw.ba.kigali_barber.permissions.PermissionRepository;
import rw.ba.kigali_barber.permissions.entity.PermissionEntity;
import rw.ba.kigali_barber.role.dto.RoleRequestDto;
import rw.ba.kigali_barber.role.dto.RoleResponseDto;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<RoleResponseDto> getAll() {
        return roleRepository.findAll()
            .stream()
            .map(RoleMapper::toResponseDto)
            .toList();
    }

    public RoleResponseDto getById(UUID id) {
        RoleEntity entity = findEntityById(id);
        return RoleMapper.toResponseDto(entity);
    }

    @Transactional
    public RoleResponseDto create(RoleRequestDto dto) {
        RoleEntity entity = RoleMapper.toEntity(dto);
        entity.setPermissions(resolvePermissions(dto.getPermissionIds()));
        RoleEntity saved = roleRepository.save(entity);
        return RoleMapper.toResponseDto(saved);
    }

    @Transactional
    public RoleResponseDto update(UUID id, RoleRequestDto dto) {
        RoleEntity entity = findEntityById(id);
        entity.setRole(dto.getName());
        entity.setPermissions(resolvePermissions(dto.getPermissionIds()));
        RoleEntity saved = roleRepository.save(entity);
        return RoleMapper.toResponseDto(saved);
    }

    @Transactional
    public void delete(UUID id) {
        RoleEntity entity = findEntityById(id);
        roleRepository.delete(entity);
    }

    @Transactional
    public RoleResponseDto assignPermissions(UUID roleId, Set<UUID> permissionIds) {
        RoleEntity entity = findEntityById(roleId);
        entity.getPermissions().addAll(resolvePermissions(permissionIds));
        RoleEntity saved = roleRepository.save(entity);
        return RoleMapper.toResponseDto(saved);
    }

    @Transactional
    public RoleResponseDto revokePermission(UUID roleId, UUID permissionId) {
        RoleEntity entity = findEntityById(roleId);
        entity.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
        RoleEntity saved = roleRepository.save(entity);
        return RoleMapper.toResponseDto(saved);
    }

    private RoleEntity findEntityById(UUID id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new RoleNotFoundException(id));
    }

    private Set<PermissionEntity> resolvePermissions(Set<UUID> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }
        List<PermissionEntity> found = permissionRepository.findAllById(permissionIds);
        if (found.size() != permissionIds.size()) {
            UUID missingId = permissionIds.stream()
                .filter(id -> found.stream().noneMatch(p -> p.getId().equals(id)))
                .findFirst()
                .orElseThrow();
            throw new PermissionNotFoundException(missingId);
        }
        return new HashSet<>(found);
    }
}
