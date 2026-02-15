package com.ecommerce.auth.service;

import com.ecommerce.auth.domain.Address;
import com.ecommerce.auth.domain.Role;
import com.ecommerce.auth.domain.User;
import com.ecommerce.auth.dto.AddressResponse;
import com.ecommerce.auth.dto.CreateUserRequest;
import com.ecommerce.auth.dto.CreateUserResponse;
import com.ecommerce.auth.repository.RoleRepository;
import com.ecommerce.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    public UserManagementService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 AddressService addressService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressService  = addressService;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (request.getAddress() == null) {
            throw new RuntimeException("Address is required");
        }
        if (request.getAddress().getPhoneNumber() == null) {
            throw new RuntimeException("Phone number is required");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        user.setPhoneNumber(request.getAddress().getPhoneNumber());

        List<Role> roles = roleRepository.findByNameIn(request.getRoles());
        if (roles.size() != request.getRoles().size()) {
            throw new RuntimeException("One or more roles not found");
        }
        user.setRoles(roles);

        User saved = userRepository.saveAndFlush(user);

        AddressResponse address  =
                addressService.addAddress(saved.getId(), request.getAddress());

        return new CreateUserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                address
        );
    }
}
