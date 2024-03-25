package com.carter.surveyorbackend.data.source;

import com.carter.surveyorbackend.data.repo.AirportRepository;
import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.data.repo.entity.AirportLounge;
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
public class AirportDataSourceTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportDataSource airportDataSource;

    @Test
    public void shouldGetAirportData() {
        List<Airport> expected = List.of(
                new Airport(
                        "ABC",
                        "airport",
                        List.of(new AirportLounge(1L, "lounge", null, Collections.emptyList()))
                )
        );

        when(airportRepository.findAll()).thenReturn(expected);

        var result = airportDataSource.getAll();
        assertThat(result).isEqualTo(expected);
    }
}
