package com.example.plant_manager.plant.model.functions;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantsModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class PlantsToModelFunction implements Function<List<Plant>, PlantsModel>, Serializable {

    @Override
    public PlantsModel apply(List<Plant> entity) {
        return PlantsModel.builder()
                .plants(entity.stream()
                        .map(plant -> PlantsModel.Plant.builder()
                                .id(plant.getId())
                                .name(plant.getName())
                                .build())
                        .toList())
                .build();
    }
}

