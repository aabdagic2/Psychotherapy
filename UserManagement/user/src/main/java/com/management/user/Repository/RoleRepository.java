package com.management.user.Repository;

import com.management.user.models.RoleEntity;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface RoleRepository extends CrudRepository<RoleEntity, String> {

}