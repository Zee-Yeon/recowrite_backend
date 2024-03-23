package com.write.reco.repository;

import com.write.reco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = :email and u.status = 'ACTIVE'")
    Optional<User> findByEmail(@Param("email") String email);
}
