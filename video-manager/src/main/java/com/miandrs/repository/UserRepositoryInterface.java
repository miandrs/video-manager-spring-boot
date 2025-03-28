package com.miandrs.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miandrs.models.entity.User;

public interface UserRepositoryInterface extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);
}
