package com.example.plant_manager.plant.dto.functions;

import com.example.plant_manager.plant.dto.GetPlantsResponse;
import com.example.plant_manager.plant.entity.Plant;

import java.util.List;
import java.util.function.Function;

public class PlantsToResponseFunction implements Function<List<Plant>, GetPlantsResponse> {

    @Override
    public GetPlantsResponse apply(List<Plant> entities) {
        return GetPlantsResponse.builder()
                .plants(entities.stream()
                        .map(plant-> GetPlantsResponse.Plant.builder()
                                .id(plant.getId())
                                .name(plant.getName())
                                .build())
                        .toList())
                .build();
    }
}
