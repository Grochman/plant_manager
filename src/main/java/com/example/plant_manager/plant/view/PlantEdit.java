package com.example.plant_manager.plant.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantEditModel;
import com.example.plant_manager.plant.service.PlantService;
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
public class PlantEdit implements Serializable {

    private final PlantService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlantEditModel plant;

    @Inject
    public PlantEdit(PlantService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Plant> plant = service.find(id);
        if (plant.isPresent()) {
            this.plant = factory.plantToEditModel().apply(plant.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Plant not found");
        }
    }

    public String saveAction() {
        service.update(factory.updatePlant().apply(service.find(id).orElseThrow(), plant));
        //String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        //return viewId + "?faces-redirect=true&includeViewParams=true";
        return "/species/species_list.xhtml?faces-redirect=true";
    }
}
