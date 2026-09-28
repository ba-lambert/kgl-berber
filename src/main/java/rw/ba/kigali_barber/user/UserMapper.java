package rw.ba.kigali_barber.user;

import rw.ba.kigali_barber.user.dto.UserResponseDto;

public class UserMapper {
    public static UserResponseDto toDto(UserEntity dto){
        return new UserResponseDto(
            dto.getUsername(),
            dto.getEmail(),
            dto.getRole().getRole()
        );
    }
}
