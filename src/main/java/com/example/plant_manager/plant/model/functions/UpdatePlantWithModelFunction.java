package com.example.plant_manager.plant.model.functions;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantEditModel;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdatePlantWithModelFunction implements BiFunction<Plant, PlantEditModel, Plant>, Serializable {

    @Override
    @SneakyThrows
    public Plant apply(Plant entity, PlantEditModel request) {
        return Plant.builder()
                .id(entity.getId())
                .name(request.getName())
                .age(request.getAge())
                .description(request.getDescription())
                .lastWateringDate(request.getLastWateringDate())
                .species(entity.getSpecies())
                .creationDateTime(entity.getCreationDateTime())
                .owner(entity.getOwner())
                .build();
    }
}