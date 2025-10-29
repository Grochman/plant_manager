package com.example.plant_manager.plant.dto.functions;

import com.example.plant_manager.plant.dto.PutPlantRequest;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.species.entity.Species;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPlantFunction  implements BiFunction<UUID, PutPlantRequest, Plant> {

    @Override
    public Plant apply(UUID id, PutPlantRequest request) {
        return Plant.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .age(request.getAge())
                .species(Species.builder()
                        .id(request.getSpecies())
                        .build())
                .build();
    }

}
