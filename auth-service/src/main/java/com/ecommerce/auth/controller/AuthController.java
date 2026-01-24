package com.ecommerce.auth.controller;

import com.ecommerce.auth.config.OpenApiConfig;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.auth.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserManagementService userManagementService;

    public AuthController(AuthService authService,
                          UserManagementService userManagementService) {
        this.authService = authService;
        this.userManagementService = userManagementService;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Secured endpoint to validate JWT and return user info
     * Used mainly for testing and client-side validation
     */
    @Operation(
            summary = "Get current authenticated user",
            description = "Returns information of the currently authenticated user"
    )
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> me(Authentication authentication) {

        return ResponseEntity.ok(
                new UserInfoResponse(
                        authentication.getName(),
                        authentication.getAuthorities()
                )
        );
    }

    @Operation(
            summary = "Create new user",
            description = "Public endpoint to register a new user"
    )
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userManagementService.createUser(request));
    }
// TODO: Add API to update, delete users and reset password
    /**
     * Simple response DTO for /me endpoint
     */
    public record UserInfoResponse(
            String userId,
            Collection<? extends GrantedAuthority> roles
    ) {}
}
