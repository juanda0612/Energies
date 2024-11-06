package com.ProyectRenewableEnergiesBackend.repository;

import com.ProyectRenewableEnergiesBackend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    boolean existsByNameAndYear(String name, Integer year);
    Optional<Location> findByNameAndYear(String name, Integer year);
}
