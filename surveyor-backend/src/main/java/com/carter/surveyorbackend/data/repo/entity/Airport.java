package com.carter.surveyorbackend.data.repo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents an airport stored in the database.
 */
@Entity
@Data
@Table(name = "ea_airport")
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
    @Id
    private String airportId;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "airport")
    @JsonManagedReference
    private List<AirportLounge> lounges;
}
