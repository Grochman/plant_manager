package com.example.plant_manager.plant.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime; // NOWY IMPORT
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlantsModel implements Serializable {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Plant {
        private UUID id;
        private String name;
        private LocalDateTime creationDateTime;
        private LocalDateTime updateDateTime;
    }

    @Singular
    private List<Plant> plants;
}