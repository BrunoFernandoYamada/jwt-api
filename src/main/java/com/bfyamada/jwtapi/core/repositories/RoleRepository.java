package com.bfyamada.jwtapi.core.repositories;

import com.bfyamada.jwtapi.core.enums.ERole;
import com.bfyamada.jwtapi.core.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);

    @Override
    List<Role> findAll();
}
