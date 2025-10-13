package com.example.plant_manager.plant.entity;

import java.time.LocalDate;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Plant {
    private String name;
    private LocalDate lastWateringDate;
    private String description;
    private Integer age;

    private User owner;
    private Species species;
}
