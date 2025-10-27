package com.example.plant_manager.plant.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlantEditModel {
    private String name;
    private LocalDate lastWateringDate;
    private String description;
    private Integer age;
}
