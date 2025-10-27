package com.example.plant_manager.plant.model.functions;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantModel;

import java.io.Serializable;
import java.util.function.Function;

public class PlantToModelFunction implements Function<Plant, PlantModel>, Serializable {

    @Override
    public PlantModel apply(Plant entity) {
        return PlantModel.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .lastWateringDate(entity.getLastWateringDate())
                .species(entity.getSpecies().getCommonName())
                .build();
    }

}

