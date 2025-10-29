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
import jakarta.ws.rs.core.Response;
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
    @Path("/plants")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPlantsResponse getPlants() {
        return factory.plantsToResponse().apply(service.findAll());
    }

    @GET
    @Path("/plants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetPlantResponse getPlant(@PathParam("id") UUID id) {
        return service.find(id)
                .map(factory.plantToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/plants/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SneakyThrows
    public void putPlant(@PathParam("id") UUID id, PutPlantRequest request) {
        try {
            service.create(factory.requestToPlant().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(PlantController.class, "getPlant")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @PATCH
    @Path("/plants/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchPlant(@PathParam("id") UUID id, PatchPlantRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updatePlant().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @DELETE
    @Path("/plants/{id}")
    public void deletePlant(@PathParam("id") UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}

