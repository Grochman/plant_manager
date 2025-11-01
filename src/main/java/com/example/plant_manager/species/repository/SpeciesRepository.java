package com.example.plant_manager.species.repository;

import com.example.plant_manager.datastore.DataStore;
import com.example.plant_manager.species.entity.Species;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SpeciesRepository {
    private final DataStore dataStore;

    @Inject
    public SpeciesRepository(DataStore dataStore) {this.dataStore = dataStore;}

    public Optional<Species> find(UUID id) {
        return dataStore.findSpecies(id);
    }

    public List<Species> findAll() {
        return dataStore.findAllSpecies();
    }

    public void create(Species entity)  throws IllegalArgumentException {
        dataStore.createSpecies(entity);
    }

    public void update(Species entity)  throws IllegalArgumentException { dataStore.updateSpecies(entity);}

    public void delete(UUID id) { dataStore.deleteSpecies(id);}
}
