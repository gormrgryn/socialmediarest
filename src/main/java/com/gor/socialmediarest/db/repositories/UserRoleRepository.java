package com.gor.socialmediarest.db.repositories;

import com.gor.socialmediarest.db.entities.UserRoleEntity;
import com.gor.socialmediarest.security.UserRole;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByName(UserRole name);
}
