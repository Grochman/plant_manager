package com.example.plant_manager.plant.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@EqualsAndHashCode
public class Plant implements Serializable {
    private UUID id;
    private String name;
    private LocalDate lastWateringDate;
    private String description;
    private Integer age;

    private User owner;
    private Species species;
}
