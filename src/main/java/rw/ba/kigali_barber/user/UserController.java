package rw.ba.kigali_barber.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.ba.kigali_barber.user.dto.UserResponseDto;

@RestController 
@RequestMapping ("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping ("")
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }
}
