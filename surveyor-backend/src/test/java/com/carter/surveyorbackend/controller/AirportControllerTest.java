package com.carter.surveyorbackend.controller;

import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.data.repo.entity.AirportLounge;
import com.carter.surveyorbackend.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest {

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();
    }

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
    public void shouldReturnAirports() throws Exception {
        var mockData = getMockData();
        when(airportService.getAllAirports()).thenReturn(mockData);

        mockMvc.perform(get("/airports").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].airportId").value("ABC"))
                .andExpect(jsonPath("$.[0].name").value("airport1"))
                .andExpect(jsonPath("$.[0].lounges[0].airportLoungeId").value("1"))
                .andExpect(jsonPath("$.[0].lounges[0].name").value("lounge1"))
                .andExpect(jsonPath("$.[1].airportId").value("DEF"))
                .andExpect(jsonPath("$.[1].name").value("airport2"))
                .andExpect(jsonPath("$.[1].lounges[0].airportLoungeId").value("2"))
                .andExpect(jsonPath("$.[1].lounges[0].name").value("lounge2"));
    }
}