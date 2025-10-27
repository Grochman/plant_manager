package com.example.plant_manager.plant.model;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlantCreateModel {
    private UUID id;
    private String name;
    private LocalDate lastWateringDate;
    private String description;
    private Integer age;

    private SpeciesModel species;
}
