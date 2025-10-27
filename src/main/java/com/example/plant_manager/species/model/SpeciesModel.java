package com.example.plant_manager.species.model;

import com.example.plant_manager.species.entity.Species;
import lombok.*;

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
}
