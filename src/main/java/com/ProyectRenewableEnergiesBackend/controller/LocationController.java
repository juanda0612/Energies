package com.ProyectRenewableEnergiesBackend.controller;

import com.ProyectRenewableEnergiesBackend.model.Location;
import com.ProyectRenewableEnergiesBackend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> add(@RequestBody Location location) {
        Location newLocation = locationService.add(location);
        if(newLocation == null) {
            return new ResponseEntity<>(newLocation, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        List<Location> locations = locationService.getAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable("id") int id) {
        Optional<Location> location = locationService.getById(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateById(@PathVariable("id") int id, @RequestBody Location location) {
        Location newLocation = new Location();
        if( location.getName() != null) {
            newLocation.setName(location.getName());
        }
        if( location.getYear() != null) {
            newLocation.setYear(location.getYear());
        }
        Location updateLocation = locationService.updateById(id, newLocation);
        return new ResponseEntity<>(updateLocation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Location> deleteById(@PathVariable("id") int id) {
        locationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{name}/{year}")
    public Optional<Location> find(@PathVariable("name") String name, @PathVariable("year") int year) {
        return locationService.getLocationsByNameAndYear(name, year);
    }
}
