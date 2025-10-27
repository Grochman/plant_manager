package com.example.plant_manager.species.model.functions;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.SpeciessModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class SpeciessToModelFunction implements Function<List<Species>, SpeciessModel>, Serializable {

    @Override
    public SpeciessModel apply(List<Species> entity) {
        return SpeciessModel.builder()
                .species(entity.stream()
                        .map(species -> SpeciessModel.Species.builder()
                                .id(species.getId())
                                .name(species.getCommonName())
                                .build())
                        .toList())
                .build();
    }
}

