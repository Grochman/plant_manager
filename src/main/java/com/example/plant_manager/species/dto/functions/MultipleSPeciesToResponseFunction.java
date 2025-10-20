package com.example.plant_manager.species.dto.functions;

import com.example.plant_manager.species.dto.GetMultipleSpeciesResponse;
import com.example.plant_manager.species.entity.Species;

import java.util.List;
import java.util.function.Function;

public class MultipleSPeciesToResponseFunction implements Function<List<Species>, GetMultipleSpeciesResponse> {

    @Override
    public GetMultipleSpeciesResponse apply(List<Species> entities) {
        return GetMultipleSpeciesResponse.builder()
                .species(entities.stream()
                        .map(species-> GetMultipleSpeciesResponse.Species.builder()
                                .id(species.getId())
                                .name(species.getCommonName())
                                .build())
                        .toList())
                .build();
    }
}
