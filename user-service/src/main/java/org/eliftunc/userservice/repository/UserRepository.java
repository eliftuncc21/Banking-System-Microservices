package org.eliftunc.userservice.repository;

import org.eliftunc.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    UserProjection findProjectionByUserId(Long userId);
}