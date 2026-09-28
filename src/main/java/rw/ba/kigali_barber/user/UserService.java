package rw.ba.kigali_barber.user;

import java.util.List;

import org.springframework.stereotype.Service;

import rw.ba.kigali_barber.user.dto.UserResponseDto;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }
}
