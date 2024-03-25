package com.carter.surveyorbackend.data.source;

import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.data.repo.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data source class for handling all airport database table interactions.
 */
@Component
@RequiredArgsConstructor
public class AirportDataSource {

    private final AirportRepository airportRepository;

    /**
     * Gets all the airports stored in the database.
     * @return A List of Airport objects containing all the airport data.
     */
    public List<Airport> getAll() {
        return this.airportRepository.findAll();
    }
}
