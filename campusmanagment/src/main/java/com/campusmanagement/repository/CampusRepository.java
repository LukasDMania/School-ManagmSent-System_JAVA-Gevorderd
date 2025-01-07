package com.campusmanagement.repository;

import com.campusmanagement.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends JpaRepository<Campus, String> {

    // Campus based on parkeerplaatsen greater than x

}
