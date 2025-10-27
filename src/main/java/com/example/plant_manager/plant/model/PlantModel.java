package com.example.plant_manager.plant.model;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlantModel {
    private String name;
    private LocalDate lastWateringDate;
    private String description;
    private int age;

    private String species;
}
