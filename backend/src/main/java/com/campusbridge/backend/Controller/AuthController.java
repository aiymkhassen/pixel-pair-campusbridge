package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.RegisterRequest;
import com.campusbridge.backend.dto.RegisterResponse;
import com.campusbridge.backend.dto.LoginRequest;
import com.campusbridge.backend.dto.LoginResponse;
import com.campusbridge.backend.entity.User;
import com.campusbridge.backend.service.RegistrationService;
import com.campusbridge.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginService loginService;

    public AuthController(
            RegistrationService registrationService,
            LoginService loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
           @Valid @RequestBody RegisterRequest request) {

        User user = registrationService.register(
                request.fullName(),
                request.email(),
                request.password());

        RegisterResponse response = new RegisterResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

   @PostMapping("/login")
public ResponseEntity<LoginResponse> login(
        @RequestBody LoginRequest request,
        HttpServletRequest httpRequest,
        HttpServletResponse httpResponse) {

    User user = loginService.login(
            request.email(),
            request.password());

    httpRequest.getSession(true);
    httpRequest.changeSessionId();

    var authentication =
        new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            null,
            List.of(new SimpleGrantedAuthority(
                "ROLE_" + user.getRole().name()))
        );

    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);

    new HttpSessionSecurityContextRepository()
        .saveContext(context, httpRequest, httpResponse);

    LoginResponse response = new LoginResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole().name(),
            "Login successful");

    return ResponseEntity.ok(response);
}

@GetMapping("/me")
public ResponseEntity<LoginResponse> me(Principal principal) {

    User user = loginService.findByEmail(principal.getName());

    return ResponseEntity.ok(new LoginResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole().name(),
            "Authenticated user"
    ));
}

@PostMapping("/logout")
public ResponseEntity<String> logout(
        HttpServletRequest request) {

    var session = request.getSession(false);

    if (session != null) {
        session.invalidate();
    }

    SecurityContextHolder.clearContext();

    return ResponseEntity.ok("Logout successful");
}
}