package com.example.plant_manager.plant.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.model.PlantModel;
import com.example.plant_manager.plant.service.PlantService;
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
public class PlantView implements Serializable {

    private PlantService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlantModel plant;

    @Inject
    public PlantView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(PlantService service) {this.service = service;}

    public void init() throws IOException {
        Optional<Plant> plant = service.find(id);
        if (plant.isPresent()) {
            this.plant = factory.plantToModel().apply(plant.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Plant not found");
        }
    }
}
