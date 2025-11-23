package com.example.plant_manager.species.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.model.PlantsModel; // Ważny import
import com.example.plant_manager.plant.service.PlantService;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.species.service.SpeciesService;
import jakarta.ejb.EJB;
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

    private SpeciesService service;
    private final ModelFunctionFactory factory;
    private final PlantService plantService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SpeciesModel species;

    @Inject
    public SpeciesView(ModelFunctionFactory factory,  PlantService plantService) {
        this.factory = factory;
        this.plantService = plantService;
    }

    @EJB
    public void setService(SpeciesService service) {this.service = service;}

    public void init() throws IOException {
        Optional<Species> speciesEntity = service.find(id);
        if (speciesEntity.isPresent()) {
            this.species = factory.speciesToModel().apply(speciesEntity.get());

            var filteredPlants = plantService.findAllBySpecies(id).orElse(java.util.Collections.emptyList());

            PlantsModel plantsModel = factory.plantsToModel().apply(filteredPlants);
            this.species.setPlants(plantsModel);

        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Species not found");
        }
    }

    public String deleteAction(PlantsModel.Plant plant) {
        plantService.delete(plant.getId());
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}