package com.example.plant_manager.species.entity;

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
public class Species {
    private String fullName;
    private String commonName;
    private String family;
    private String description;
    private Integer wateringRateInDays;
    private LightType lightType;
    private String origin;
}

enum LightType{
    LOW, MEDIUM, HIGH;
}
