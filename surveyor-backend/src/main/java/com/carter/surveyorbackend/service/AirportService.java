package com.carter.surveyorbackend.service;

import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.data.source.AirportDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for interacting with airport data.
 */
@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportDataSource airportDataSource;

    /**
     * Fetches all airports stored in the database.
     * @return A List of Airport objects.
     */
    public List<Airport> getAllAirports() {
        return airportDataSource.getAll();
    }

}
