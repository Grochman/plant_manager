package com.example.plant_manager.species.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantModel;
import com.example.plant_manager.plant.model.PlantsModel;
import com.example.plant_manager.plant.service.PlantService;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.species.service.SpeciesService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class SpeciesView implements Serializable {

    private final SpeciesService service;

    private final ModelFunctionFactory factory;

    private final PlantService plantService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SpeciesModel species;

    @Inject
    public SpeciesView(SpeciesService service, ModelFunctionFactory factory,  PlantService plantService) {
        this.service = service;
        this.factory = factory;
        this.plantService = plantService;
    }

    public void init() throws IOException {
        Optional<Species> species = service.find(id);
        if (species.isPresent()) {
            this.species = factory.speciesToModel().apply(species.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Species not found");
        }
    }


    public String deleteAction(PlantsModel.Plant plant) {
        plantService.delete(plant.getId());
        Optional<Species> updated = service.find(id);
        updated.ifPresent(value -> this.species = factory.speciesToModel().apply(value));
        return "species_list?faces-redirect=true";
    }
}
