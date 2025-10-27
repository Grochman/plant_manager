package com.example.plant_manager.species.model.functions;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.LightTypeModel;

import java.io.Serializable;
import java.util.function.Function;

public class LightTypeToModelFunction  implements Function<Species.LightType, LightTypeModel>, Serializable {
    @Override
    public LightTypeModel apply(Species.LightType entity) {
        return LightTypeModel.builder()
                .type(entity.name())
                .build();
    }

}

