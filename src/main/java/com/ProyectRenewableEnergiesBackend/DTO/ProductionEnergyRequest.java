package com.ProyectRenewableEnergiesBackend.DTO;

import com.ProyectRenewableEnergiesBackend.model.TypeEnergy;

public class ProductionEnergyRequest {
    private String country;
    private Integer year;

    private TypeEnergy type_energy;
    private Double value;

    public ProductionEnergyRequest() {
    }

    public ProductionEnergyRequest(String country, Integer year, TypeEnergy type_energy, Double value) {
        this.country = country;
        this.year = year;
        this.type_energy = type_energy;
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public TypeEnergy getType_energy() {
        return type_energy;
    }

    public void setType_energy(TypeEnergy type_energy) {
        this.type_energy = type_energy;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
