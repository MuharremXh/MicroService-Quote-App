package com.rremimicroservices.userservice.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

}
