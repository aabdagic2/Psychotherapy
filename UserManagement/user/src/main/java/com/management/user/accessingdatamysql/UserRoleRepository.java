package com.management.user.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, UUID> {

}
