package rw.ba.kigali_barber.auth;

import org.springframework.stereotype.Service;

import rw.ba.kigali_barber.user.UserRepository;

@Service 
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String userSignup(){
        return "user created";
    }
}
