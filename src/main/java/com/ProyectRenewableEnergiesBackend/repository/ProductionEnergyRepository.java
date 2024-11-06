package com.ProyectRenewableEnergiesBackend.repository;

import com.ProyectRenewableEnergiesBackend.model.Location;
import com.ProyectRenewableEnergiesBackend.model.ProductionEnergy;
import com.ProyectRenewableEnergiesBackend.model.TypeEnergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionEnergyRepository extends JpaRepository<ProductionEnergy, Integer> {
    boolean existsByTypeEnergyAndLocation(TypeEnergy type_energy, Optional<Location> location);

}
