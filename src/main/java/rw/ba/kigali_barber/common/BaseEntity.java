package rw.ba.kigali_barber.common;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass 
@Getter 
@Setter 
public abstract class BaseEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column( name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @Column ( name = "updated_at", nullable = false)
    private  Instant updatedAt;

    @PrePersist 
    protected void onCreate(){
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate 
    protected void onUpdate(){
        Instant now = Instant.now();
        updatedAt = now;
    }
}
