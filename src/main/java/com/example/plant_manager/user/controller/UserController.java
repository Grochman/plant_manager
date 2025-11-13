package com.example.plant_manager.user.controller;

import com.example.plant_manager.component.DtoFunctionFactory;
import com.example.plant_manager.species.dto.GetMultipleSpeciesResponse;
import com.example.plant_manager.user.dto.GetUsersResponse;
import com.example.plant_manager.user.dto.PutUserRequest;
import com.example.plant_manager.user.entity.UserRoles;
import com.example.plant_manager.user.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserController {
    private UserService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public UserController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(UserService service) {this.service = service;}

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @PUT
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SneakyThrows
    public void putPlant(@PathParam("id") UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }
}

