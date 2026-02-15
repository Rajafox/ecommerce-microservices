package com.ecommerce.auth.controller;

import com.ecommerce.auth.dto.AddressRequest;
import com.ecommerce.auth.dto.AddressResponse;
import com.ecommerce.auth.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Address Management")
@RestController
@RequestMapping("/auth/addresses")
@SecurityRequirement(name = "bearerAuth")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Add a new address for the user")
    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(
            Authentication authentication,
            @Valid @RequestBody AddressRequest request) {

        Long userId = Long.parseLong(authentication.getName());
        AddressResponse response = addressService.addAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all addresses for the user")
    @GetMapping
    public ResponseEntity<List<AddressResponse>> getUserAddresses(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<AddressResponse> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Get a specific address")
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(
            Authentication authentication,
            @Parameter(name = "addressId", in = ParameterIn.PATH, description = "Address ID", required = true)
            @PathVariable("addressId")  Long addressId
            ) {

        Long userId = Long.parseLong(authentication.getName());
        AddressResponse address = addressService.getAddress(userId, addressId);
        return ResponseEntity.ok(address);
    }

    @Operation(summary = "Get the default address for the user")
    @GetMapping("/default")
    public ResponseEntity<AddressResponse> getDefaultAddress(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        AddressResponse address = addressService.getDefaultAddress(userId);
        return ResponseEntity.ok(address);
    }

    @Operation(summary = "Update an address")
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            Authentication authentication,
            @Parameter(name = "addressId", in = ParameterIn.PATH, description = "Address ID", required = true)
            @PathVariable("addressId")  Long addressId
            ,
            @Valid @RequestBody AddressRequest request) {

        Long userId = Long.parseLong(authentication.getName());
        AddressResponse response = addressService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Set an address as default")
    @PatchMapping("/{addressId}/default")
    public ResponseEntity<AddressResponse> setDefaultAddress(
            Authentication authentication,
            @Parameter(name = "addressId", in = ParameterIn.PATH, description = "Address ID", required = true)
            @PathVariable("addressId")  Long addressId
    ) {

        Long userId = Long.parseLong(authentication.getName());
        AddressResponse response = addressService.setDefaultAddress(userId, addressId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an address")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            Authentication authentication,
            @Parameter(name = "addressId", in = ParameterIn.PATH, description = "Address ID", required = true)
            @PathVariable("addressId")  Long addressId)
    {

        Long userId = Long.parseLong(authentication.getName());
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
