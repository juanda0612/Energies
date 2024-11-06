package com.ProyectRenewableEnergiesBackend.service;

import com.ProyectRenewableEnergiesBackend.model.Location;
import com.ProyectRenewableEnergiesBackend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location add(Location location) {
        if(!existsByNameAndYear(location.getName(), location.getYear())) {
            return locationRepository.save(location);
        }
        return null;
    }

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Optional<Location> getById(int id) {
        return locationRepository.findById(id);
    }

    public Location updateById(int id, Location location) {
        return locationRepository.findById(id)
                .map(oldLocation -> {
                    if (location.getName() != null) {
                        oldLocation.setName(location.getName());
                    }
                    if (location.getYear() != null) {
                        oldLocation.setYear(location.getYear());
                    }
                    return locationRepository.save(oldLocation);
                })
                .orElseThrow(() -> new RuntimeException("Location with id " + id + " not found"));
    }

    public void deleteById(int id) {
        locationRepository.deleteById(id);
    }

    public boolean existsByNameAndYear(String name, Integer year) {
        return locationRepository.existsByNameAndYear(name, year);
    }

    public Optional<Location> getLocationsByNameAndYear(String name, Integer year) {
        return locationRepository.findByNameAndYear(name, year);
    }

    public long getCount() {
        return locationRepository.count();
    }
}
