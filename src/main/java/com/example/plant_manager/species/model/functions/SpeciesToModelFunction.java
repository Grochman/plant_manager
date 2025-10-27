package com.example.plant_manager.species.model.functions;

import com.example.plant_manager.plant.model.PlantsModel;
import com.example.plant_manager.plant.model.functions.PlantsToModelFunction;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.LightTypeModel;
import com.example.plant_manager.species.model.SpeciesModel;

import java.util.List;
import java.util.function.Function;
import java.io.Serializable;


public class SpeciesToModelFunction implements Function<Species, SpeciesModel>, Serializable {
    private final PlantsToModelFunction plantsToModelFunction = new PlantsToModelFunction();

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
                .lightType(
                        entity.getLightType() != null
                                ? LightTypeModel.builder()
                                .type(entity.getLightType().name())
                                .build()
                                : null
                )
                .plants(
                        entity.getPlantList() == null
                                ? PlantsModel.builder().plants(java.util.List.of()).build()
                                : plantsToModelFunction.apply(entity.getPlantList())
                )
                .build();
    }
}
