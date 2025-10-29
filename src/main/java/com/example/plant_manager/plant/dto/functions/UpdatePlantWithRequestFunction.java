package com.example.plant_manager.plant.dto.functions;

import com.example.plant_manager.plant.dto.PatchPlantRequest;
import com.example.plant_manager.plant.entity.Plant;

import java.util.function.BiFunction;

public class UpdatePlantWithRequestFunction  implements BiFunction<Plant, PatchPlantRequest, Plant> {
    @Override
    public Plant apply(Plant entity, PatchPlantRequest request) {
        return Plant.builder()
                .id(entity.getId())
                .name(request.getName())
                .description(request.getDescription())
                .age(request.getAge())
                .species(entity.getSpecies())
                .build();
    }
}
