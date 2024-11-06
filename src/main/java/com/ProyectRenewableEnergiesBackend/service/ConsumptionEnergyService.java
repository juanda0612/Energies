package com.ProyectRenewableEnergiesBackend.service;

import com.ProyectRenewableEnergiesBackend.DTO.ConsumptionEnergyRequest;
import com.ProyectRenewableEnergiesBackend.model.ConsumptionEnergy;
import com.ProyectRenewableEnergiesBackend.model.Location;
import com.ProyectRenewableEnergiesBackend.model.TypeEnergy;
import com.ProyectRenewableEnergiesBackend.repository.ConsumptionEnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionEnergyService {

    @Autowired
    private ConsumptionEnergyRepository consumptionEnergyRepository;
    @Autowired
    private LocationService locationService;

    public ConsumptionEnergy add(ConsumptionEnergy consumption) {
        return consumptionEnergyRepository.save(consumption);
    }

    public List<ConsumptionEnergy> getAll() {
        return consumptionEnergyRepository.findAll();
    }

    public Optional<ConsumptionEnergy> getById(int id) {
        return consumptionEnergyRepository.findById(id);
    }

    public ConsumptionEnergy updateById(int id, ConsumptionEnergy consumption) {
        return consumptionEnergyRepository.findById(id)
                .map(oldConsumption -> {
                    if (consumption.getValue() != null) {
                        oldConsumption.setValue(consumption.getValue());
                    }
                    return consumptionEnergyRepository.save(oldConsumption);
                })
                .orElseThrow(() -> new RuntimeException("Location with id " + id + " not found"));
    }

    public void deleteById(int id) {
        consumptionEnergyRepository.deleteById(id);
    }

    public ConsumptionEnergy CreateconsumptionEnergy(ConsumptionEnergyRequest consumption) {
        Optional<Location> location = locationService.getLocationsByNameAndYear(
                consumption.getCountry(), consumption.getYear()
        );

        if (checkConsumptionExists(consumption.getType_energy(), location)) {
            return null;
        }

        ConsumptionEnergy consumptionEnergy = new ConsumptionEnergy();
        consumptionEnergy.setType_energy(consumption.getType_energy());
        consumptionEnergy.setValue(consumption.getValue());
        location.ifPresent(consumptionEnergy::setLocation);

        return consumptionEnergyRepository.save(consumptionEnergy);
    }

    public boolean checkConsumptionExists(TypeEnergy type_energy, Optional<Location> location) {
        return consumptionEnergyRepository.existsByTypeEnergyAndLocation(type_energy, location);
    }

    public long getCount() {
        return consumptionEnergyRepository.count();
    }
}
