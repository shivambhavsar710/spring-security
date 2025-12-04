package com.JwtToken.repository;

import com.JwtToken.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{

    Optional<Users> findByUsername(String username);

}