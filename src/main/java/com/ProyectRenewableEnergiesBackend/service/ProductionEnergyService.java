package com.ProyectRenewableEnergiesBackend.service;

import com.ProyectRenewableEnergiesBackend.DTO.ProductionEnergyRequest;
import com.ProyectRenewableEnergiesBackend.model.Location;
import com.ProyectRenewableEnergiesBackend.model.ProductionEnergy;
import com.ProyectRenewableEnergiesBackend.model.TypeEnergy;
import com.ProyectRenewableEnergiesBackend.repository.ProductionEnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionEnergyService {

    @Autowired
    private ProductionEnergyRepository productionEnergyRepository;
    @Autowired
    private LocationService locationService;


    public ProductionEnergy add(ProductionEnergy production) {
        return productionEnergyRepository.save(production);
    }

    public List<ProductionEnergy> getAll() {
        return productionEnergyRepository.findAll();
    }

    public Optional<ProductionEnergy> getById(int id) {
        return productionEnergyRepository.findById(id);
    }

    public ProductionEnergy updateById(int id, ProductionEnergy production) {
        return productionEnergyRepository.findById(id)
                .map(oldProduction -> {
                    if (production.getValue() != null) {
                        oldProduction.setValue(production.getValue());
                    }
                    return productionEnergyRepository.save(oldProduction);
                })
                .orElseThrow(() -> new RuntimeException("Location with id " + id + " not found"));
    }

    public void deleteById(int id) {
        productionEnergyRepository.deleteById(id);
    }

    public ProductionEnergy createProductionEnergy(ProductionEnergyRequest production) {
        Optional<Location> location = locationService.getLocationsByNameAndYear(
                production.getCountry(), production.getYear()
        );

        if (checkProductionExists(production.getType_energy(), location)) {
            return null;
        }

        ProductionEnergy productionEnergy = new ProductionEnergy();
        productionEnergy.setType_energy(production.getType_energy());
        productionEnergy.setValue(production.getValue());
        location.ifPresent(productionEnergy::setLocation);

        return productionEnergyRepository.save(productionEnergy);
    }

    public boolean checkProductionExists(TypeEnergy type_energy, Optional<Location> location) {
        return productionEnergyRepository.existsByTypeEnergyAndLocation(type_energy, location);
    }

    public long getCount() {
        return productionEnergyRepository.count();
    }
}
