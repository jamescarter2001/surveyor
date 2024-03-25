package com.carter.surveyorbackend.data.repo;

import com.carter.surveyorbackend.data.repo.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for abstracting the airport database table connection.
 */
public interface AirportRepository extends JpaRepository<Airport, String> {
}
