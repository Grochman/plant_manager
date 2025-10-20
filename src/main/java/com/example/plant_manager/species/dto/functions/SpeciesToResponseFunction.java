package com.example.plant_manager.species.dto.functions;

import com.example.plant_manager.species.dto.GetSpeciesResponse;
import com.example.plant_manager.species.entity.Species;

import java.util.function.Function;

public class SpeciesToResponseFunction implements Function<Species, GetSpeciesResponse> {
    @Override
    public GetSpeciesResponse apply(Species entity) {
        return GetSpeciesResponse.builder()
                .id(entity.getId())
                .name(entity.getCommonName())
                .build();
    }
}
