package com.example.plant_manager.species.dto.functions;

import com.example.plant_manager.plant.dto.PutPlantRequest;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.species.dto.PutSpeciesRequest;
import com.example.plant_manager.species.entity.Species;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToSpeciesFunction  implements BiFunction<UUID, PutSpeciesRequest, Species> {

    @Override
    public Species apply(UUID id, PutSpeciesRequest request) {
        return Species.builder()
                .id(id)
                .fullName(request.getFullName())
                .commonName(request.getCommonName())
                .description(request.getDescription())
                .build();
    }

}

