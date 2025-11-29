package com.example.plant_manager.plant.model;

import com.example.plant_manager.species.model.SpeciesModel;
import com.example.plant_manager.validation.Capitalized;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Nazwa nie może być pusta")
    @Size(min = 3, max = 50, message = "Nazwa musi mieć od 3 do 50 znaków")
    @Capitalized(message = "Nazwa rośliny musi zaczynać się z wielkiej litery")
    private String name;

    private LocalDate lastWateringDate;

    @Size(min = 3, max = 50, message = "Nazwa musi mieć od 3 do 50 znaków")
    @Capitalized(message = "Nazwa rośliny musi zaczynać się z wielkiej litery")
    private String description;

    @Min(value = 0, message = "Wiek musi być liczbą dodatnią")
    private Integer age;

    @NotNull(message = "Gatunek jest wymagany")
    private SpeciesModel species;
}