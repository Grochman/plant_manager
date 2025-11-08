package com.example.plant_manager.species.entity;

import com.example.plant_manager.plant.entity.Plant;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "species")
public class Species implements Serializable {
    public enum LightType{
        LOW, MEDIUM, HIGH;
    }

    @Id
    private UUID id;
    private String fullName;
    private String commonName;
    private String family;
    private String description;
    private Integer wateringRateInDays;
    private LightType lightType;
    private String origin;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plantList;
}

