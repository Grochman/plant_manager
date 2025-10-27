package com.example.plant_manager.plant.model.functions;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class PlantToEditModelFunction implements Function<Plant, PlantEditModel>, Serializable {

    @Override
    public PlantEditModel apply(Plant entity) {
        return PlantEditModel.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .lastWateringDate(entity.getLastWateringDate())
                .age(entity.getAge())
                .build();
    }

}

