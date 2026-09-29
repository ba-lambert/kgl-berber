package rw.ba.kigali_barber.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rw.ba.kigali_barber.auth.dto.ForgotPasswordRequestDto;
import rw.ba.kigali_barber.auth.dto.LoginRequestDto;
import rw.ba.kigali_barber.auth.dto.ResetPasswordRequestDto;
import rw.ba.kigali_barber.common.response.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequestDto dto) {
        return new ApiResponse<>(true, "success", authService.login(dto));
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto dto) {
        return new ApiResponse<>(true, "success", authService.forgotPassword(dto));
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@Valid @RequestBody ResetPasswordRequestDto dto) {
        return new ApiResponse<>(true, "success", authService.resetPassword(dto));
    }
}
