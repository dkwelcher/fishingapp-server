package com.fishinglog.fishingapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representation of a fishing trip, mapping to the 'trips' table in the database.
 *
 * @since 2024-10-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trips")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    private LocalDate date;

    private String bodyOfWater;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
