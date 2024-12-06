package com.campusmanagment.repository;

import com.campusmanagment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // User based on age greater than x
}
