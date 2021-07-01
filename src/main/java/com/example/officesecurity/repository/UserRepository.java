package com.example.officesecurity.repository;


import com.example.officesecurity.model.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<UserDAO, String> {
    List<UserDAO> findAll();

    UserDAO findByUserName(String userName);


}