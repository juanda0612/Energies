package com.ProyectRenewableEnergiesBackend.service;

import com.ProyectRenewableEnergiesBackend.model.ConsumptionEnergy;
import com.ProyectRenewableEnergiesBackend.model.Location;
import com.ProyectRenewableEnergiesBackend.model.ProductionEnergy;
import com.ProyectRenewableEnergiesBackend.model.TypeEnergy;
import com.ProyectRenewableEnergiesBackend.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataLoadService implements CommandLineRunner {

    @Autowired
    private LocationService locationService;
    @Autowired
    private ProductionEnergyService productionEnergyService;
    @Autowired
    private ConsumptionEnergyService consumptionEnergyService;

    private final CsvReader csvReader = new CsvReader();
    private final List<String[]> dataConsumptionList = csvReader.getData(
            "src/main/resources/data/renewable-energy-consumption.csv");
    private final List<String[]> dataProductionList = csvReader.getData(
            "src/main/resources/data/renewable-energy-production.csv");

    public void dataLoadLocationConsumption() {
        List<String[]> data = new ArrayList<>(dataConsumptionList);
        data.removeFirst();
        data.removeIf(row -> row.length < 7);

        if (locationService.getCount() >= data.size()) {
            return;
        }

        for (String[] row : data) {
            String country = row[0];
            Integer year = Integer.parseInt(row[2]);

            if (!locationService.existsByNameAndYear(country, year)) {
                locationService.add(createLocation(country, year));
            }
        }
    }

    public void dataLoadLocationProduction() {
        List<String[]> data = new ArrayList<>(dataProductionList);
        data.removeFirst();
        data.removeIf(row -> row.length < 7);

        if (locationService.getCount() >= data.size()) {
            return;
        }

        for (String[] row : data) {
            String country = row[0];
            Integer year = Integer.parseInt(row[2]);

            if (!locationService.existsByNameAndYear(country, year)) {
                locationService.add(createLocation(country, year));
            }
        }
    }

    public void dataLoadConsumption() {
        List<String[]> data = new ArrayList<>(dataConsumptionList);
        data.removeFirst();
        data.removeIf(row -> row.length < 7);

        if (consumptionEnergyService.getCount() >= data.size()) {
            return;
        }

        for (String[] row : data) {
            Optional<Location> location = locationService
                    .getLocationsByNameAndYear(row[0], Integer.parseInt(row[2]));

            if (!consumptionEnergyService.checkConsumptionExists(TypeEnergy.solar, location)) {
                consumptionEnergyService.add(
                        createConsumptionEnergy(
                                location,
                                TypeEnergy.solar,
                                (row[4].isEmpty()) ? 0 : Double.parseDouble(row[4])
                        )
                );
            }

            if (!consumptionEnergyService.checkConsumptionExists(TypeEnergy.wind, location)) {
                consumptionEnergyService.add(
                        createConsumptionEnergy(
                                location,
                                TypeEnergy.wind,
                                (row[5].isEmpty()) ? 0 : Double.parseDouble(row[5])
                        )
                );
            }

            if (!consumptionEnergyService.checkConsumptionExists(TypeEnergy.hydro, location)) {
                consumptionEnergyService.add(
                        createConsumptionEnergy(
                                location,
                                TypeEnergy.hydro,
                                (row[6].isEmpty()) ? 0 : Double.parseDouble(row[6])
                        )
                );
            }
        }
    }

    public void dataLoadProduction() {
        List<String[]> data = new ArrayList<>(dataProductionList);
        data.removeFirst();
        data.removeIf(row -> row.length < 7);

        if (productionEnergyService.getCount() >= data.size()) {
            return;
        }

        for (String[] row : data) {
            Optional<Location> location = locationService
                    .getLocationsByNameAndYear(row[0], Integer.parseInt(row[2]));

            if (!productionEnergyService.checkProductionExists(TypeEnergy.solar, location)) {
                productionEnergyService.add(
                        createProductionEnergy(
                                location,
                                TypeEnergy.solar,
                                (row[5].isEmpty()) ? 0 : Double.parseDouble(row[5])
                        )
                );
            }

            if (!productionEnergyService.checkProductionExists(TypeEnergy.wind, location)) {
                productionEnergyService.add(
                        createProductionEnergy(
                                location,
                                TypeEnergy.wind,
                                (row[3].isEmpty()) ? 0 : Double.parseDouble(row[3])
                        )
                );
            }

            if (!productionEnergyService.checkProductionExists(TypeEnergy.hydro, location)) {
                productionEnergyService.add(
                        createProductionEnergy(
                                location,
                                TypeEnergy.hydro,
                                (row[4].isEmpty()) ? 0 : Double.parseDouble(row[4])
                        )
                );
            }
        }
    }

    public Location createLocation(String country, Integer year) {
        Location location = new Location();
        location.setName(country);
        location.setYear(year);
        return location;
    }

    public ConsumptionEnergy createConsumptionEnergy(Optional<Location> location, TypeEnergy typeEnergy, double value) {
        ConsumptionEnergy consumption = new ConsumptionEnergy();
        consumption.setType_energy(typeEnergy);
        consumption.setValue(value);
        location.ifPresent(consumption::setLocation);
        return consumption;
    }

    public ProductionEnergy createProductionEnergy(Optional<Location> location, TypeEnergy typeEnergy, double value) {
        ProductionEnergy production = new ProductionEnergy();
        production.setType_energy(typeEnergy);
        production.setValue(value);
        location.ifPresent(production::setLocation);
        return production;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Cargando datos..."); // aproximadamente 1 minuto para cargar los datos
        dataLoadLocationConsumption();
        dataLoadLocationProduction();
        dataLoadConsumption();
        dataLoadProduction();
        System.out.println("Carga de datos completada");
    }
}
