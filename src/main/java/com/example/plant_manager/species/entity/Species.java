package com.example.plant_manager.species.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@EqualsAndHashCode
public class Species implements Serializable {
    public enum LightType{
        LOW, MEDIUM, HIGH;
    }

    private UUID id;
    private String fullName;
    private String commonName;
    private String family;
    private String description;
    private Integer wateringRateInDays;
    private LightType lightType;
    private String origin;
}

