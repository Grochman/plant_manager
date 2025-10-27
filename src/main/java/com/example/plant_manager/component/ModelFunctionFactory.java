package com.example.plant_manager.component;

import com.example.plant_manager.plant.model.functions.*;
import com.example.plant_manager.species.model.functions.LightTypeToModelFunction;
import com.example.plant_manager.species.model.functions.SpeciesToModelFunction;
import com.example.plant_manager.species.model.functions.SpeciessToModelFunction;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

@ApplicationScoped
public class ModelFunctionFactory {
    public PlantToModelFunction plantToModel() {
        return new PlantToModelFunction();
    }

    public PlantsToModelFunction plantsToModel() {
        return new PlantsToModelFunction();
    }

    public PlantToEditModelFunction plantToEditModel() {
        return new PlantToEditModelFunction();
    }

    public ModelToPlantFunction modelToPlant() {
        return new ModelToPlantFunction();
    }

    public SpeciesToModelFunction speciesToModel() {
        return new SpeciesToModelFunction();
    }

    public SpeciessToModelFunction speciessToModel() {
        return new SpeciessToModelFunction();
    }

    public LightTypeToModelFunction lightTypeToModel() {
        return new LightTypeToModelFunction();
    }

    public UpdatePlantWithModelFunction updatePlant() {
        return new UpdatePlantWithModelFunction();
    }

}

