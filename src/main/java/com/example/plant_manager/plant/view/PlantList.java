package com.example.plant_manager.plant.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.model.PlantsModel;
import com.example.plant_manager.plant.service.PlantService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.UUID;

@RequestScoped
@Named
public class PlantList {
    private PlantService service;

    private PlantsModel plants;

    private final ModelFunctionFactory factory;

    @Inject
    public PlantList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(PlantService service) {this.service = service;}

    public PlantsModel getPlants() {
        if (plants == null) {
            plants = factory.plantsToModel().apply(service.findAllForCallerPrincipal());
        }
        return plants;
    }

    public String deleteAction(PlantsModel.Plant plant) {
        service.delete(plant.getId());
        return "plant_list?faces-redirect=true";
    }
}
