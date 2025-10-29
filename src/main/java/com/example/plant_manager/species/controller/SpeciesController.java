package com.example.plant_manager.species.controller;

import com.example.plant_manager.component.DtoFunctionFactory;
import com.example.plant_manager.species.dto.GetMultipleSpeciesResponse;
import com.example.plant_manager.species.dto.GetSpeciesResponse;
import com.example.plant_manager.species.dto.PatchSpeciesRequest;
import com.example.plant_manager.species.dto.PutSpeciesRequest;
import com.example.plant_manager.species.service.SpeciesService;
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
public class SpeciesController {

    private final SpeciesService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public SpeciesController(
            SpeciesService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @GET
    @Path("/species")
    @Produces(MediaType.APPLICATION_JSON)
    public GetMultipleSpeciesResponse getMultipleSpecies() {
        return factory.multipleSpeciesToResponse().apply(service.findAll());
    }

    @GET
    @Path("/species/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetSpeciesResponse getSpecies(@PathParam("id") UUID id) {
        return service.find(id)
                .map(factory.speciesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/species/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SneakyThrows
    public void putSpecies(@PathParam("id") UUID id, PutSpeciesRequest request) {
        try {
            service.create(factory.requestToSpecies().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(com.example.plant_manager.species.controller.SpeciesController.class, "getSpecies")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @PATCH
    @Path("/species/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchSpecies(@PathParam("id") UUID id, PatchSpeciesRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateSpecies().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @DELETE
    @Path("/species/{id}")
    public void deleteSpecies(@PathParam("id") UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
