package com.example.plant_manager.species.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.model.PlantsModel;
import com.example.plant_manager.plant.service.PlantService;
import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.species.model.SpeciessModel;
import com.example.plant_manager.species.service.SpeciesService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class SpeciesList {
    private final SpeciesService service;

    private SpeciessModel species;

    private final ModelFunctionFactory factory;

    @Inject
    public SpeciesList(SpeciesService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public SpeciessModel getSpecies() {
        if (species == null) {
            species = factory.speciessToModel().apply(service.findAll());
        }
        return species;
    }

    public String deleteAction(SpeciessModel.Species species) {
        service.delete(species.getId());
        return "species_list?faces-redirect=true";
    }
}
