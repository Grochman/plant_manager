package com.example.plant_manager.plant.model;

import com.example.plant_manager.validation.Capitalized;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank()
    @Size(min = 3, max = 50)
    @Capitalized()
    private String name;

    private LocalDate lastWateringDate;

    @NotBlank()
    @Size(min = 3, max = 50)
    @Capitalized()
    private String description;

    @Min(value = 0)
    private Integer age;
}