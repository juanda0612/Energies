package com.ProyectRenewableEnergiesBackend.model;

import jakarta.persistence.*;

@Entity
public class ProductionEnergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnergy typeEnergy;
    private Double value;

    @ManyToOne(targetEntity = Location.class)
    private Location location;

    public ProductionEnergy() {
    }

    public ProductionEnergy(Integer id, TypeEnergy type_energy, Double value, Location location) {
        this.id = id;
        this.typeEnergy = type_energy;
        this.value = value;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeEnergy getType_energy() {
        return typeEnergy;
    }

    public void setType_energy(TypeEnergy type_energy) {
        this.typeEnergy = type_energy;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
