package com.campusmanagment.repository;

import com.campusmanagment.model.Lokaal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LokaalRepository extends JpaRepository<Lokaal, Long> {
}
