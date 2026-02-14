package com.ecommerce.auth.service;

import com.ecommerce.auth.domain.Address;
import com.ecommerce.auth.domain.User;
import com.ecommerce.auth.dto.AddressRequest;
import com.ecommerce.auth.dto.AddressResponse;
import com.ecommerce.auth.repository.AddressRepository;
import com.ecommerce.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AddressResponse addAddress(Long userId, AddressRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getIsDefault() || user.getAddresses().isEmpty()) {
            user.getAddresses().forEach(addr -> addr.setIsDefault(false));
            request.setIsDefault(true);
        }
        Address address = new Address();
        address.setStreetAddress(request.getStreetAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAddressType(request.getAddressType());
        address.setIsDefault(request.getIsDefault());
        address.setUser(user);
        address.setCreatedAt(Instant.now());
        address.setUpdatedAt(Instant.now());
        Address saved = addressRepository.save(address);
        return mapToResponse(saved);
    }

    @Transactional
    public AddressResponse updateAddress(Long userId, Long addressId, AddressRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = addressRepository.findByIdAndUserId(addressId, userId).orElseThrow(() -> new RuntimeException("Address not found"));
        address.setStreetAddress(request.getStreetAddress());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAddressType(request.getAddressType());
        address.setUpdatedAt(Instant.now());
        if (request.getIsDefault()) {
            user.getAddresses().forEach(addr -> addr.setIsDefault(false));
            address.setIsDefault(true);
        }
        Address updated = addressRepository.save(address);
        return mapToResponse(updated);
    }

    public List<AddressResponse> getUserAddresses(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return addressRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public AddressResponse getAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId).orElseThrow(() -> new RuntimeException("Address not found"));
        return mapToResponse(address);
    }

    public AddressResponse getDefaultAddress(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = addressRepository.findByUserIdAndIsDefault(userId, true).orElseThrow(() -> new RuntimeException("No default address"));
        return mapToResponse(address);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId).orElseThrow(() -> new RuntimeException("Address not found"));
        if (address.getIsDefault()) {
            List<Address> otherAddresses = addressRepository.findByUserId(userId).stream().filter(addr -> !addr.getId().equals(addressId)).collect(Collectors.toList());
            if (!otherAddresses.isEmpty()) {
                otherAddresses.get(0).setIsDefault(true);
                addressRepository.save(otherAddresses.get(0));
            }
        }
        addressRepository.delete(address);
    }

    @Transactional
    public AddressResponse setDefaultAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId).orElseThrow(() -> new RuntimeException("Address not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getAddresses().forEach(addr -> addr.setIsDefault(false));
        address.setIsDefault(true);
        address.setUpdatedAt(Instant.now());
        Address updated = addressRepository.save(address);
        return mapToResponse(updated);
    }

    private AddressResponse mapToResponse(Address address) {
        return new AddressResponse(address.getId(), address.getStreetAddress(), address.getCity(), address.getState(),
                address.getPostalCode(), address.getCountry(), address.getPhoneNumber(), address.getAddressType(),
                address.getIsDefault(), address.getCreatedAt(), address.getUpdatedAt());
    }
}