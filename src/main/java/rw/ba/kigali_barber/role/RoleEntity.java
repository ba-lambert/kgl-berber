package rw.ba.kigali_barber.role;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import rw.ba.kigali_barber.common.BaseEntity;
import rw.ba.kigali_barber.permissions.entity.PermissionEntity;

@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "role", nullable = false, unique = true)
    private String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions = new HashSet<>();

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(PermissionEntity permission) {
        permissions.add(permission);
    }

    public void removePermission(PermissionEntity permission) {
        permissions.remove(permission);
    }
}
