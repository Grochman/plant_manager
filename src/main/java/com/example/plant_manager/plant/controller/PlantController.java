package com.example.plant_manager.plant.controller;

import com.example.plant_manager.component.DtoFunctionFactory;
import com.example.plant_manager.plant.dto.GetPlantResponse;
import com.example.plant_manager.plant.dto.GetPlantsResponse;
import com.example.plant_manager.plant.dto.PatchPlantRequest;
import com.example.plant_manager.plant.dto.PutPlantRequest;
import com.example.plant_manager.plant.service.PlantService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;

import java.util.UUID;

@Path("")
public class PlantController {

    private final PlantService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public PlantController(
            PlantService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @GET
    @Path("/species/{speciesId}/plants")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPlantsResponse getPlants(@PathParam("speciesId") UUID id) {
        return service.findAllBySpecies(id)
                .map(factory.plantsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @GET
    @Path("/species/{speciesId}/plants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPlantResponse getPlant(@PathParam("id") UUID id, @PathParam("speciesId") UUID speciesId) {
        return service.findByIdAndSpecies(id, speciesId)
                .map(factory.plantToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("/species/{speciesId}/plants/{id}")
    public void deletePlant(@PathParam("id") UUID id, @PathParam("speciesId") UUID speciesId) {
        service.findByIdAndSpecies(id, speciesId)
                .ifPresentOrElse(
                        entity -> service.delete(id),
                        () -> {
                            throw new NotFoundException();
                        }
                );
    }

    @PATCH
    @Path("/species/{speciesId}/plants/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchPlant(@PathParam("id") UUID id, @PathParam("speciesId") UUID speciesId, PatchPlantRequest request) {
        service.findByIdAndSpecies(id, speciesId).ifPresentOrElse(
                entity -> service.update(factory.updatePlant().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @PUT
    @Path("/species/{speciesId}/plants/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SneakyThrows
    public void putPlant(@PathParam("id") UUID id, @PathParam("speciesId") UUID speciesId, PutPlantRequest request) {
        try {
            service.create(factory.requestToPlant().apply(id, speciesId, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }
}

