package com.carter.surveyorbackend.controller;

import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    /**
     * GET endpoint for retrieving airport, lounge and facility data from the database.
     * @return A ResponseEntity containing a List of all the available location data.
     */
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }
}
