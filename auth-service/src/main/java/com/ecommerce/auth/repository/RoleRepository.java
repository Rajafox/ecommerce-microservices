package com.ecommerce.auth.repository;

import com.ecommerce.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByNameIn(Collection<String> names);
}
