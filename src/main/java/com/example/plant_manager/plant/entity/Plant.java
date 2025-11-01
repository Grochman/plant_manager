package com.example.plant_manager.plant.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "species")
    private Species species;
}
