package rw.ba.kigali_barber.permissions;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.ba.kigali_barber.permissions.entity.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
}
