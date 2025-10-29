package com.example.plant_manager.species.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutSpeciesRequest {
    public String lightType;
    private String fullName;
    private String commonName;
    private String family;
    private String description;
    private Integer wateringRateInDays;
    private String origin;
}


