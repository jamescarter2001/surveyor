package com.carter.surveyorbackend;

import com.carter.surveyorbackend.data.repo.AirportRepository;
import com.carter.surveyorbackend.data.repo.QuestionRepository;
import com.carter.surveyorbackend.data.repo.QuestionTypeRepository;
import com.carter.surveyorbackend.data.repo.SurveyQuestionResponseRepository;
import com.carter.surveyorbackend.data.repo.SurveyRepository;
import com.carter.surveyorbackend.data.repo.entity.Airport;
import com.carter.surveyorbackend.data.repo.entity.AirportLounge;
import com.carter.surveyorbackend.data.repo.entity.Facility;
import com.carter.surveyorbackend.data.repo.entity.Question;
import com.carter.surveyorbackend.data.repo.entity.QuestionType;
import com.carter.surveyorbackend.data.repo.entity.QuestionTypeOption;
import com.carter.surveyorbackend.data.repo.entity.Survey;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestion;
import com.carter.surveyorbackend.data.repo.entity.SurveyQuestionResponse;
import com.carter.surveyorbackend.model.dto.SurveyQuestionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class SurveyorBackendIntegrationTest {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16.1-alpine"
    );

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @MockBean
    private Clock clock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private SurveyQuestionResponseRepository surveyQuestionResponseRepository;

    @AfterEach
    public void afterEach() {
        airportRepository.deleteAll();
        surveyRepository.deleteAll();
        questionRepository.deleteAll();
    }

    private Airport injectAirportMockData() {
        var airport = new Airport("ABC", "airport", new ArrayList<>());
        var airportLounge = new AirportLounge(null, "lounge", airport, new ArrayList<>());
        var facility = new Facility(null, airportLounge, "facility", "description", "http://image");
        airportLounge.getFacilities().add(facility);
        airport.getLounges().add(airportLounge);

        List<Airport> entities = List.of(airport);

        airportRepository.saveAll(entities);

        return airport;
    }

    private Survey injectSurveyMockData() {
        var questionType = new QuestionType(null, "QuestionType", new ArrayList<>());

        var questionTypeOption = new QuestionTypeOption(null, questionType, 1, "option1");
        questionType.getOptions().add(questionTypeOption);

        questionTypeRepository.save(questionType);

        var question1 = new Question(null, "dummy question 1", questionType);
        var question2 = new Question(null, "dummy question 2", questionType);

        questionRepository.save(question1);
        questionRepository.save(question2);

        var survey = new Survey(null, "test survey", Timestamp.valueOf("2020-01-01 00:00:00.000000000"), Timestamp.valueOf("2030-01-01 00:00:00.000000000"), Collections.emptyList());

        surveyRepository.save(survey);

        SurveyQuestion surveyQuestion1 = new SurveyQuestion(survey, question1, 1);
        SurveyQuestion surveyQuestion2 = new SurveyQuestion(survey, question2, 2);

        survey.setConnections(List.of(surveyQuestion1, surveyQuestion2));
        surveyRepository.save(survey);

        return survey;
    }

    @Test
    public void contextLoads() {
        assertNotNull(applicationContext);
        assertNotNull(webApplicationContext);
    }

    /**
     * Integration test that verifies database extraction and delivery of airport data to the UI.
     */
    @Test
    public void shouldGetAirports() throws Exception {
        injectAirportMockData();

        mockMvc.perform(get("/airports").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].airportId").value("ABC"))
                .andExpect(jsonPath("$.[0].name").value("airport"))
                .andExpect(jsonPath("$.[0].lounges[0].name").value("lounge"));
    }

    /**
     * Integration test that verifies database extraction and delivery of survey data to the UI.
     */
    @Test
    public void shouldGetSurveys() throws Exception {
        var survey = injectSurveyMockData();

        mockMvc.perform(get("/surveys")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].surveyId").value(survey.getSurveyId()),
                        jsonPath("$.[0].name").value("test survey"),
                        jsonPath("$.[0].startTime").value("2020-01-01T00:00:00.000+00:00"),
                        jsonPath("$.[0].endTime").value("2030-01-01T00:00:00.000+00:00"),
                        jsonPath("$.[0].connections[0].question.questionId").value(survey.getConnections().get(0).getQuestion().getQuestionId()),
                        jsonPath("$.[0].connections[0].question.body").value("dummy question 1"),
                        jsonPath("$.[0].connections[0].question.type.name").value("QuestionType")
                );
    }

    @Test
    public void shouldSaveSurveyResponse() throws Exception {
        var airport = injectAirportMockData();
        var survey = injectSurveyMockData();

        var surveyId = String.valueOf(survey.getSurveyId());
        var questionId1 = String.valueOf(survey.getConnections().get(0).getQuestion().getQuestionId());
        var questionId2 = String.valueOf(survey.getConnections().get(1).getQuestion().getQuestionId());
        var facilityId = String.valueOf(airport.getLounges().get(0).getFacilities().get(0).getFacilityId());

        List<SurveyQuestionResponseDTO> dtos = List.of(
                SurveyQuestionResponseDTO.builder()
                        .surveyId(surveyId)
                        .questionId(questionId1)
                        .facilityId(facilityId)
                        .response("response 1")
                        .build(),
                SurveyQuestionResponseDTO.builder()
                        .surveyId(surveyId)
                        .questionId(questionId2)
                        .facilityId(facilityId)
                        .response("response 2")
                        .build()
        );

        String json = new ObjectMapper().writeValueAsString(dtos);

        // Mock clock time, truncated to account for lower database datetime precision.
        var now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        when(Instant.now(clock)).thenReturn(now);

        // Assert that the POST response contains the expected data.
        mockMvc.perform(post("/surveys/response")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", "application/json")
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].surveyId").value(surveyId),
                        jsonPath("$.[0].questionId").value(questionId1),
                        jsonPath("$.[0].facilityId").value(facilityId),
                        jsonPath("$.[0].timestamp").exists(),
                        jsonPath("$.[0].response").value("response 1"),
                        jsonPath("$.[1].surveyId").value(surveyId),
                        jsonPath("$.[1].questionId").value(questionId2),
                        jsonPath("$.[1].facilityId").value(facilityId),
                        jsonPath("$.[1].timestamp").exists(),
                        jsonPath("$.[1].response").value("response 2")
                );

        List<SurveyQuestionResponse> expectedResponses = List.of(
                SurveyQuestionResponse.builder()
                        .surveyQuestionResponseId(1L)
                        .surveyId(Long.valueOf(surveyId))
                        .questionId(Long.valueOf(questionId1))
                        .facilityId(Long.valueOf(facilityId))
                        .timestamp(Timestamp.from(now))
                        .response("response 1")
                        .build(),
                SurveyQuestionResponse.builder()
                        .surveyQuestionResponseId(2L)
                        .surveyId(Long.valueOf(surveyId))
                        .questionId(Long.valueOf(questionId2))
                        .facilityId(Long.valueOf(facilityId))
                        .timestamp(Timestamp.from(now))
                        .response("response 2")
                        .build()
        );

        // Assert that the responses are persisted to the database.
        assertThat(surveyQuestionResponseRepository.findAll()).isEqualTo(expectedResponses);
    }
}
