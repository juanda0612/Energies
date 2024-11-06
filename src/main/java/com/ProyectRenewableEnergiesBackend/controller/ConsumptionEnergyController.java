package com.ProyectRenewableEnergiesBackend.controller;

import com.ProyectRenewableEnergiesBackend.DTO.ConsumptionEnergyRequest;
import com.ProyectRenewableEnergiesBackend.model.ConsumptionEnergy;
import com.ProyectRenewableEnergiesBackend.service.ConsumptionEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consumption")
public class ConsumptionEnergyController {

    @Autowired
    private ConsumptionEnergyService consumptionEnergyService;

    @PostMapping
    public ResponseEntity<ConsumptionEnergy> add(@RequestBody ConsumptionEnergyRequest consumption) {
        ConsumptionEnergy newConsumptionEnergy = consumptionEnergyService.CreateconsumptionEnergy(consumption);
        if(newConsumptionEnergy == null) {
            return new ResponseEntity<>(newConsumptionEnergy, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newConsumptionEnergy, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConsumptionEnergy>> getAll() {
        List<ConsumptionEnergy> consumptionEnergyList = consumptionEnergyService.getAll();
        return new ResponseEntity<>(consumptionEnergyList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumptionEnergy> getById(@PathVariable("id") int id) {
        Optional<ConsumptionEnergy> consumptionEnergy = consumptionEnergyService.getById(id);
        return consumptionEnergy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumptionEnergy> updateById(@PathVariable("id") int id, @RequestBody ConsumptionEnergy consumption) {
        ConsumptionEnergy newConsumtion = new ConsumptionEnergy();
        if(consumption.getValue() != null) {
            newConsumtion.setValue(consumption.getValue());
        }
        ConsumptionEnergy updateConsumption = consumptionEnergyService.updateById(id, newConsumtion);
        return new ResponseEntity<>(updateConsumption, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ConsumptionEnergy> deleteById(@PathVariable("id") int id) {
        consumptionEnergyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
