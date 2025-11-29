package com.example.plant_manager.plant.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "species")
@Builder
@EqualsAndHashCode(exclude = "species")
@Entity
@Table(name = "plants")
public class Plant implements Serializable {
    @Id
    private UUID id;
    private String name;
    private LocalDate lastWateringDate;
    private String description;
    private Integer age;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    @PrePersist
    public void prePersist() {
        creationDateTime = LocalDateTime.now();
        updateDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateDateTime = LocalDateTime.now();
    }
}