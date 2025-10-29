package com.example.plant_manager.plant.view;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.plant.model.PlantCreateModel;
import com.example.plant_manager.plant.service.PlantService;
import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.species.service.SpeciesService;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class PlantCreate implements Serializable {
    private final PlantService plantService;

    private final SpeciesService speciesService;

    private final ModelFunctionFactory factory;

    @Getter
    private PlantCreateModel plant;

    @Getter
    private List<SpeciesModel> species;

    private final Conversation conversation;

    @Inject
    public PlantCreate(
            PlantService plantService,
            SpeciesService speciesService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.plantService = plantService;
        this.factory = factory;
        this.speciesService = speciesService;
        this.conversation = conversation;
    }

    public void init() {

        if (conversation.isTransient()) {
            species = speciesService.findAll().stream()
                    .map(factory.speciesToModel())
                    .collect(Collectors.toList());
            plant = PlantCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }

    }

    public String cancelAction() {
        conversation.end();
        return "/species/species_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        plantService.create(factory.modelToPlant().apply(plant));
        conversation.end();
        return "/species/species_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }

}

