package com.example.plant_manager.plant.dto.functions;

import com.example.plant_manager.plant.dto.PutPlantRequest;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.species.entity.Species;

import java.util.UUID;;

public class RequestToPlantFunction {

    public Plant apply(UUID id, UUID speciesId, PutPlantRequest request) {
        return Plant.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .age(request.getAge())
                .species(Species.builder()
                        .id(speciesId)
                        .build())
                .build();
    }

}
