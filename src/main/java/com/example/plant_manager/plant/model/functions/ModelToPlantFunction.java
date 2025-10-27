package com.example.plant_manager.plant.model.functions;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantCreateModel;
import com.example.plant_manager.species.entity.Species;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToPlantFunction  implements Function<PlantCreateModel, Plant>, Serializable {

    @Override
    @SneakyThrows
    public Plant apply(PlantCreateModel model) {
        return Plant.builder()
                .id(model.getId())
                .name(model.getName())
                .age(model.getAge())
                .description(model.getDescription())
                .lastWateringDate(model.getLastWateringDate())
                .species(Species.builder()
                        .id(model.getSpecies().getId())
                        .build())
                .build();
    }
}

