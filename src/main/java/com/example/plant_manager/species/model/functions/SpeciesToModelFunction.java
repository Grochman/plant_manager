package com.example.plant_manager.species.model.functions;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.SpeciesModel;

import java.util.function.Function;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;


public class SpeciesToModelFunction implements Function<Species, SpeciesModel>, Serializable {
    @Override
    public SpeciesModel apply(Species entity) {
        return SpeciesModel.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .family(entity.getFamily())
                .description(entity.getDescription())
                .wateringRateInDays(entity.getWateringRateInDays())
                .origin(entity.getOrigin())
                .commonName(entity.getCommonName())
                .build();
    }

}
