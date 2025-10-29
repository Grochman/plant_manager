package com.example.plant_manager.species.dto.functions;

import com.example.plant_manager.species.dto.PatchSpeciesRequest;
import com.example.plant_manager.species.entity.Species;

import java.util.function.BiFunction;

public class UpdateSpeciesWithRequestFunction  implements BiFunction<Species, PatchSpeciesRequest, Species> {
    @Override
    public Species apply(Species entity, PatchSpeciesRequest request) {
        return Species.builder()
                .id(entity.getId())
                .fullName(request.getFullName())
                .commonName(request.getCommonName())
                .description(request.getDescription())
                .build();
    }
}
