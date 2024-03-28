package com.management.user.Repository;

import com.management.user.models.UserEntity;
import com.management.user.models.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, String> {
    void deleteByUser(UserEntity user);

}
