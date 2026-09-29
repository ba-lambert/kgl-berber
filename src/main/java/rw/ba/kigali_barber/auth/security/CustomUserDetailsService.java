package rw.ba.kigali_barber.auth.security;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rw.ba.kigali_barber.role.RoleEntity;
import rw.ba.kigali_barber.user.UserEntity;
import rw.ba.kigali_barber.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(buildAuthorities(user.getRole()))
            .build();
    }

    private List<GrantedAuthority> buildAuthorities(RoleEntity role) {
        Stream<GrantedAuthority> roleAuthority = Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getName()));
        return Stream.concat(roleAuthority, permissionAuthorities).toList();
    }
}
