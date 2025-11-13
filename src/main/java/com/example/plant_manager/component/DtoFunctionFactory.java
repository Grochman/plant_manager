package com.example.plant_manager.component;

import com.example.plant_manager.plant.dto.functions.PlantToResponseFunction;
import com.example.plant_manager.plant.dto.functions.PlantsToResponseFunction;
import com.example.plant_manager.plant.dto.functions.RequestToPlantFunction;
import com.example.plant_manager.plant.dto.functions.UpdatePlantWithRequestFunction;
import com.example.plant_manager.species.dto.GetMultipleSpeciesResponse;
import com.example.plant_manager.species.dto.functions.MultipleSPeciesToResponseFunction;
import com.example.plant_manager.species.dto.functions.RequestToSpeciesFunction;
import com.example.plant_manager.species.dto.functions.SpeciesToResponseFunction;
import com.example.plant_manager.species.dto.functions.UpdateSpeciesWithRequestFunction;
import com.example.plant_manager.user.dto.functions.RequestToUserFunction;
import com.example.plant_manager.user.dto.functions.UserToResponseFunction;
import com.example.plant_manager.user.dto.functions.UsersToResponseFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoFunctionFactory {
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public PlantsToResponseFunction plantsToResponse() {return new PlantsToResponseFunction();}

    public PlantToResponseFunction plantToResponse() {return new PlantToResponseFunction();}

    public RequestToPlantFunction requestToPlant() {return new RequestToPlantFunction();}

    public RequestToUserFunction requestToUser() {return new RequestToUserFunction();}

    public UpdatePlantWithRequestFunction updatePlant() {return new UpdatePlantWithRequestFunction();}

    public SpeciesToResponseFunction speciesToResponse() {return new SpeciesToResponseFunction();}

    public MultipleSPeciesToResponseFunction multipleSpeciesToResponse() {return new MultipleSPeciesToResponseFunction();}

    public RequestToSpeciesFunction requestToSpecies() {return new RequestToSpeciesFunction();}

    public UpdateSpeciesWithRequestFunction updateSpecies() {return new UpdateSpeciesWithRequestFunction();}
}
