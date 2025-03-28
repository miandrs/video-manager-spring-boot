package com.miandrs.service;

import com.miandrs.models.dto.AuthDto;
import com.miandrs.models.dto.LoginDto;
import com.miandrs.models.dto.RegisterDto;
import com.miandrs.models.entity.User;
import com.miandrs.repository.UserRepositoryInterface;
import com.miandrs.service.AuthService;
import com.miandrs.utils.JwtUtils;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
    private JwtUtils jwtUtil;
	@Autowired
    private UserRepositoryInterface userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthDto login(LoginDto login) throws Exception {
        try {
            authenticate(login.getEmail(), login.getPassword());

            User user = userRepository.findByEmail(login.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtil.generateToken(user);
            
            return new AuthDto(token, user.getId());
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Incorrect login or password");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public AuthDto register(RegisterDto register) throws Exception {
        try {
            User user = createUserFromRegistration(register);
            user = userRepository.save(user);

            String token = jwtUtil.generateToken(user);
            return new AuthDto(token, user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private User createUserFromRegistration(RegisterDto register) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole());

        return user;
    }
}
