package rw.ba.kigali_barber.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import rw.ba.kigali_barber.common.BaseEntity;
import rw.ba.kigali_barber.role.RoleEntity;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column (name = "password",nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_role_id", nullable = false)
    private RoleEntity role;

    @Column(name = "email", nullable = false)
    private String email;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public RoleEntity getRole() {
        return role;
    }
    public void setRole(RoleEntity role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
