package com.example.plant_manager.species.model.converter;

import com.example.plant_manager.component.ModelFunctionFactory;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.species.service.SpeciesService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = SpeciesModel.class, managed = true)
public class SpeciesModelConverter implements Converter<SpeciesModel> {
    private final SpeciesService service;

    private final ModelFunctionFactory factory;

    @Inject
    public SpeciesModelConverter(SpeciesService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public SpeciesModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Species> species = service.find(UUID.fromString(value));
        return species.map(factory.speciesToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, SpeciesModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
