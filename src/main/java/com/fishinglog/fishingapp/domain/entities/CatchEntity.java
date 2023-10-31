package com.fishinglog.fishingapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "catches")
public class CatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catchId;

    private LocalTime time;

    private Double latitude;

    private Double longitude;

    private String species;

    private String lureOrBait;

    private String weatherCondition;

    private Integer airTemperature;

    private Integer waterTemperature;

    private Integer windSpeed;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;
}