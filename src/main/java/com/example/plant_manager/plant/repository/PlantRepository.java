package com.example.plant_manager.plant.repository;

import com.example.plant_manager.datastore.DataStore;
import com.example.plant_manager.plant.entity.Plant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PlantRepository {
    private final DataStore dataStore;

    @Inject
    public PlantRepository(DataStore dataStore) {this.dataStore = dataStore;}

    public Optional<Plant> find(UUID id) {
        return dataStore.findPlant(id);
    }

    public List<Plant> findAll() {
        return dataStore.findAllPlants();
    }

    public void create(Plant entity)  throws IllegalArgumentException {
        dataStore.createPlant(entity);
    }

    public void update(Plant entity)  throws IllegalArgumentException {
        dataStore.updatePlant(entity);
    }

    public void delete(UUID id) {dataStore.deletePlant(id);}
}
