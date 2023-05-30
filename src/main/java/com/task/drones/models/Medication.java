package com.task.drones.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name="drones_serial", nullable=true)
    private Drone drone;
}
