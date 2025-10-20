package com.example.plant_manager.plant.dto.functions;

import com.example.plant_manager.plant.dto.GetPlantResponse;
import com.example.plant_manager.plant.entity.Plant;

import java.util.function.Function;

public class PlantToResponseFunction implements Function<Plant, GetPlantResponse> {
    @Override
    public GetPlantResponse apply(Plant entity) {
        return GetPlantResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
