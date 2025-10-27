package com.example.plant_manager.species.model;

import com.example.plant_manager.plant.model.PlantsModel;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@EqualsAndHashCode
public class SpeciesModel {
    private UUID id;
    private String fullName;
    private String commonName;
    private String family;
    private String description;
    private Integer wateringRateInDays;
    private LightTypeModel lightType;
    private String origin;
    private PlantsModel plants;
}
