package com.carter.surveyorbackend.service;

import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.data.repo.entity.AirportLounge;
import com.carter.surveyorbackend.data.source.AirportDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    private AirportDataSource airportDataSource;

    @InjectMocks
    private AirportService airportService;

    private static List<Airport> getMockData() {
        return List.of(
                new Airport(
                        "ABC",
                        "airport1",
                        List.of(new AirportLounge(1L, "lounge1", null, Collections.emptyList()))
                ),
                new Airport(
                        "DEF",
                        "airport2",
                        List.of(new AirportLounge(2L, "lounge2", null, Collections.emptyList()))
                )
        );
    }

    @Test
    public void shouldGetAllAirports() {
        var mockData = getMockData();
        when(airportDataSource.getAll()).thenReturn(mockData);

        assertThat(airportService.getAllAirports()).isEqualTo(mockData);
    }
}