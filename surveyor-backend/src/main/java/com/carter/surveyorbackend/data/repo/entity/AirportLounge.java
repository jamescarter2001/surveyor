package com.carter.surveyorbackend.data.repo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents an airport lounge stored in the database.
 */
@Entity
@Data
@Table(name = "ea_airport_lounge")
@AllArgsConstructor
@NoArgsConstructor
public class AirportLounge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportLoungeId;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "airport_id")
    @JsonBackReference
    private Airport airport;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "airportLounge")
    @JsonManagedReference
    private List<Facility> facilities;
}
