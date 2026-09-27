package rw.ba.kigali_barber.permissions;

import java.util.UUID;

public class PermissionNotFoundException extends RuntimeException {

    public PermissionNotFoundException(UUID id) {
        super("Permission not found with id: " + id);
    }
}
