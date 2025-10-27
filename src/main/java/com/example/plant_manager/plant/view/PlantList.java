package com.example.plant_manager.plant.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.model.PlantsModel;
import com.example.plant_manager.plant.service.PlantService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class PlantList {
    private final PlantService service;

    private PlantsModel plants;

    private final ModelFunctionFactory factory;

    @Inject
    public PlantList(PlantService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public PlantsModel getPlants() {
        if (plants == null) {
            plants = factory.plantsToModel().apply(service.findAll());
        }
        return plants;
    }

    public String deleteAction(PlantsModel.Plant plant) {
        service.delete(plant.getId());
        return "plant_list?faces-redirect=true";
    }
}
